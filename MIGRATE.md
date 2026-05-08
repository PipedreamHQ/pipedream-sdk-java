<!-- markdownlint-disable MD024 -->
# Migrating from v1.x

This guide will help you migrate your existing Pipedream Java SDK v1.x
integration to the latest version.

## Table of contents

- [Migrating from v1.x](#migrating-from-v1x)
  - [Table of contents](#table-of-contents)
  - [Deprecation](#deprecation)
  - [Breaking changes](#breaking-changes)
  - [Bumping the dependency](#bumping-the-dependency)
    - [v1.x (old)](#v1x-old)
    - [v2.x (new)](#v2x-new)
  - [Client initialization](#client-initialization)
    - [Environment variables](#environment-variables)
    - [`PipedreamClient` (unchanged)](#pipedreamclient-unchanged)
    - [`BaseClient` direct construction](#baseclient-direct-construction)
      - [v1.x (old)](#v1x-old-1)
      - [v2.x (new)](#v2x-new-1)
  - [`ConfigurableProp` discriminated-union rewrite](#configurableprop-discriminated-union-rewrite)
    - [v1.x (old)](#v1x-old-2)
    - [v2.x (new)](#v2x-new-2)
  - [`ConfiguredPropValue` union rewrite](#configuredpropvalue-union-rewrite)
    - [v1.x (old)](#v1x-old-3)
    - [v2.x (new)](#v2x-new-3)
  - [Pagination internals removed](#pagination-internals-removed)
  - [Type model regenerations](#type-model-regenerations)
  - [New features in v2.x](#new-features-in-v2x)
    - [Built-in HTTP logging](#built-in-http-logging)
    - [Per-request query parameters](#per-request-query-parameters)
    - [SSE streams with event-level discrimination](#sse-streams-with-event-level-discrimination)
    - [Configurable retry status codes](#configurable-retry-status-codes)
  - [Migration checklist](#migration-checklist)

## Deprecation

The v1.x version of the Pipedream Java SDK is now deprecated. This means that no
changes will be made to this version unless there are critical security issues.
We recommend that you migrate to the latest version of the SDK to take advantage
of new features, improvements, and bug fixes if possible.

## Breaking changes

The new SDK version introduces a small number of breaking changes that you need
to be aware of when migrating from v1.x. Most user code that simply constructs a
`PipedreamClient` and calls namespaced methods (e.g.
`client.actions().run(...)`, `client.triggers().deploy(...)`) will continue to
work without modification. The notable changes are:

- **Coordinates / version**: the Maven artifactId `com.pipedream:pipedream` is
  unchanged. Bump the version pin to `2.0.0` (or later).
- **`ConfigurableProp` discriminated-union API rewrite**: the named static
  factories (`alert(...)`, `app(...)`, `boolean_(...)`, …) are replaced with
  overloaded `of(...)` factories, and the named visitor methods (`visitAlert`,
  `visitApp`, …) are replaced with overloaded `visit(...)` methods. The
  `isAlert()`/`isApp()`/etc. predicates and `getAlert()`/etc. accessors are
  removed.
- **`ConfiguredPropValue` union rewrite**: `ConfiguredPropValue.of(Object)` is
  removed; use a typed factory or wrap untyped values with
  `ConfiguredPropValueAny.of(...)`. The `Visitor.visit(Object)` overload is
  replaced by `visit(ConfiguredPropValueAny)`.
- **Removed pagination internals**: `com.pipedream.api.core.pagination` classes
  `CustomPager`, `BiDirectionalPage`, and `AsyncCustomPager` are deleted.
  Standard `SyncPagingIterable<T>` usage (including the async wrappers, which
  return `CompletableFuture<SyncPagingIterable<T>>`) is unchanged.
- **Type model regenerations from the Fern generator upgrade**: the SDK was
  regenerated with a newer Fern toolchain, which shuffled the declaration order
  of some enum constants (e.g. `AppAuthType`). This is source-compatible for
  normal usage but binary-incompatible — recompile your project against v2.

The rest of this guide walks through each item with before/after Java snippets.

## Bumping the dependency

If your code only ever talks to v2 through `PipedreamClient.builder()` or
`AsyncPipedreamClient.builder()`, the version bump is the only change you need
to make.

### v1.x (old)

```groovy
dependencies {
  implementation 'com.pipedream:pipedream:1.2.0'
}
```

```xml
<dependency>
  <groupId>com.pipedream</groupId>
  <artifactId>pipedream</artifactId>
  <version>1.2.0</version>
</dependency>
```

### v2.x (new)

```groovy
dependencies {
  implementation 'com.pipedream:pipedream:2.0.0'
}
```

```xml
<dependency>
  <groupId>com.pipedream</groupId>
  <artifactId>pipedream</artifactId>
  <version>2.0.0</version>
</dependency>
```

## Client initialization

Client construction is largely **unchanged** between v1 and v2.
`PipedreamClient.builder()` and `AsyncPipedreamClient.builder()` continue to
read the same environment variables, and `BaseClient.withToken(...)` /
`BaseClient.withCredentials(...)` continue to work. v2 introduces a new fluent
`BaseClient.builder()` static method as the recommended alternative.

### Environment variables

The same five environment variables are honored in v2:

```text
PIPEDREAM_BASE_URL
PIPEDREAM_CLIENT_ID
PIPEDREAM_CLIENT_SECRET
PIPEDREAM_PROJECT_ENVIRONMENT
PIPEDREAM_PROJECT_ID
```

### `PipedreamClient` (unchanged)

```java
import com.pipedream.api.PipedreamClient;

PipedreamClient client = PipedreamClient.builder()
    .clientId("your-client-id")        // or rely on PIPEDREAM_CLIENT_ID
    .clientSecret("your-client-secret")
    .projectId("your-project-id")
    .projectEnvironment("development") // or "production"
    .build();
```

### `BaseClient` direct construction

**NOTE: using `BaseClient` directly is not recommended, use `PipedreamClient`
instead. This also applies to the async variants.**

For code that directly constructs a `BaseClient`, v2 adds a new fluent
`builder()` entry point. The v1 `withToken(...)` and `withCredentials(...)`
methods remain available — only the recommended style has changed.

#### v1.x (old)

```java
import com.pipedream.api.BaseClient;

BaseClient client = BaseClient
    .withCredentials("client-id", "client-secret")
    .url("https://api.example.com")
    .build();

BaseClient tokenClient = BaseClient
    .withToken("your-access-token")
    .url("https://api.example.com")
    .build();
```

#### v2.x (new)

```java
import com.pipedream.api.BaseClient;

BaseClient client = BaseClient.builder()
    .credentials("client-id", "client-secret")
    .url("https://api.example.com")
    .build();

BaseClient tokenClient = BaseClient.builder()
    .token("your-access-token")
    .url("https://api.example.com")
    .build();
```

## `ConfigurableProp` discriminated-union rewrite

If your code constructs `ConfigurableProp` values via the
`ConfigurableProp.<variant>(...)` factories or implements
`ConfigurableProp.Visitor`, you'll need to update both call sites.

The set of variant types is unchanged (alert, any, app, boolean, dataStore, dir,
timer, apphook, integerArray, http, httpRequest, db, sql, airtableBaseId,
airtableTableId, airtableViewId, airtableFieldId, discordChannel,
discordChannelArray, integer, object, string, stringArray) — only the factory
and visitor *names* have changed.

### v1.x (old)

```java
ConfigurableProp prop = ConfigurableProp.app(myConfigurablePropApp);

String label = prop.visit(new ConfigurableProp.Visitor<String>() {
    @Override public String visitApp(ConfigurablePropApp value)         { return "app"; }
    @Override public String visitAlert(ConfigurablePropAlert value)     { return "alert"; }
    @Override public String visitString(ConfigurablePropString value)   { return "string"; }
    @Override public String visitBoolean(ConfigurablePropBoolean value) { return "boolean"; }
    // ... visitInteger, visitObject, visitDir, visitTimer, visitApphook,
    // visitDataStore, visitSql, visitHttp, visitHttpRequest, visitDb,
    // visitAirtableBaseId, visitAirtableTableId, visitAirtableViewId,
    // visitAirtableFieldId, visitDiscordChannel, visitDiscordChannelArray,
    // visitIntegerArray, visitStringArray, visitAny, visitUnknown
});

if (prop.isApp()) {
    ConfigurablePropApp app = prop.getApp();
    // ...
}
```

### v2.x (new)

```java
ConfigurableProp prop = ConfigurableProp.of(myConfigurablePropApp);

String label = prop.visit(new ConfigurableProp.Visitor<String>() {
    @Override public String visit(ConfigurablePropApp value)         { return "app"; }
    @Override public String visit(ConfigurablePropAlert value)       { return "alert"; }
    @Override public String visit(ConfigurablePropString value)      { return "string"; }
    @Override public String visit(ConfigurablePropBoolean value)     { return "boolean"; }
    // ... one overloaded visit(...) per variant type. The compiler enforces
    // exhaustiveness by failing if any overload is missing.
});

// isApp()/getApp() and friends are removed. Branch via instanceof on
// prop.get(), or use the visitor.
Object raw = prop.get();
if (raw instanceof ConfigurablePropApp) {
    ConfigurablePropApp app = (ConfigurablePropApp) raw;
    // ...
}
```

## `ConfiguredPropValue` union rewrite

If your code calls `ConfiguredPropValue.of(Object)` or implements
`ConfiguredPropValue.Visitor`, you'll need to update those call sites. The
catch-all `Object` branch is now represented by the explicit
`ConfiguredPropValueAny` wrapper type.

### v1.x (old)

```java
import com.pipedream.api.types.ConfiguredPropValue;
import java.util.List;
import java.util.Map;

// `of(Object)` was the catch-all factory for arbitrary JSON values.
ConfiguredPropValue v = ConfiguredPropValue.of(someUntypedObject);

String kind = v.visit(new ConfiguredPropValue.Visitor<String>() {
    @Override public String visit(Object value)                  { return "any"; }
    @Override public String visit(ConfiguredPropValueApp value)  { return "app"; }
    @Override public String visit(boolean value)                 { return "bool"; }
    @Override public String visit(double value)                  { return "number"; }
    @Override public String visit(Map<String, Object> value)     { return "object"; }
    @Override public String visit(ConfiguredPropValueSql value)  { return "sql"; }
    @Override public String visit(String value)                  { return "string"; }
    @Override public String visit(List<String> value)            { return "list"; }
});
```

### v2.x (new)

```java
import com.pipedream.api.types.ConfiguredPropValue;
import com.pipedream.api.types.ConfiguredPropValueAny;
import java.util.List;
import java.util.Map;

// Wrap arbitrary values explicitly with ConfiguredPropValueAny.
ConfiguredPropValue v =
    ConfiguredPropValue.of(ConfiguredPropValueAny.of(someUntypedObject));

String kind = v.visit(new ConfiguredPropValue.Visitor<String>() {
    @Override public String visit(ConfiguredPropValueApp value)   { return "app"; }
    @Override public String visit(boolean value)                  { return "bool"; }
    @Override public String visit(double value)                   { return "number"; }
    @Override public String visit(ConfiguredPropValueSql value)   { return "sql"; }
    @Override public String visit(String value)                   { return "string"; }
    @Override public String visit(List<String> value)             { return "list"; }
    @Override public String visit(Map<String, Object> value)      { return "object"; }
    @Override public String visit(ConfiguredPropValueAny value)   { return "any"; }
});
```

In summary:

- `ConfiguredPropValue.of(Object)` is gone. Use the typed factory that matches
  your value, or wrap untyped values with `ConfiguredPropValueAny.of(...)`.
- The `Visitor` no longer has `visit(Object)`. The "any" branch is now
  `visit(ConfiguredPropValueAny)`, and the visitor is exhaustive over the typed
  variants.

## Pagination internals removed

The following internal pagination helpers have been removed:

```text
com.pipedream.api.core.pagination.CustomPager        (removed)
com.pipedream.api.core.pagination.BiDirectionalPage  (removed)
com.pipedream.api.core.pagination.AsyncCustomPager   (removed)
```

The standard pagination usage continues to work via `SyncPagingIterable<T>`,
both for sync clients (returned directly) and async clients (returned wrapped in
a `CompletableFuture`):

```java
// Sync
for (Account account : client.accounts().list()) {
    System.out.println(account);
}

// Async
asyncClient.accounts().list().thenAccept(page -> {
    for (Account account : page) {
        System.out.println(account);
    }
});
```

If your code referenced any of the removed classes directly, replace it with a
`SyncPagingIterable<T>` consumer (`.iterator()`, `.streamItems()`, or an
enhanced `for` loop).

## Type model regenerations

The SDK was regenerated with a newer Fern toolchain (CLI `3.5.0` → `5.17.0`,
generator `3.27.6` → `4.8.4`). The generated source is compatible with typical
v1 call sites, but the *declaration order* of some enum constants shifted as a
side effect — `AppAuthType` is the canonical example. This is source-compatible
but binary-incompatible. After bumping to v2, recompile your project rather than
relying on previously-built `.class` files.

## New features in v2.x

The following capabilities are new in v2 and are entirely opt-in. None are
required for migration; they are listed here so you know what you pick up "for
free."

### Built-in HTTP logging

```java
import com.pipedream.api.core.LogConfig;
import com.pipedream.api.core.LogLevel;

PipedreamClient client = PipedreamClient.builder()
    .logging(LogConfig.builder()
        .level(LogLevel.DEBUG)
        .silent(false)
        .build())
    .build();
```

The SDK is silent by default — log output is only emitted when you pass an
explicit `LogConfig`.

### Per-request query parameters

`RequestOptions` now supports adding ad-hoc query parameters per request:

```java
import com.pipedream.api.core.RequestOptions;

RequestOptions opts = RequestOptions.builder()
    .addQueryParameter("trace_id", "abc-123")
    .build();

client.actions().run(myRequest, opts);
```

### SSE streams with event-level discrimination

`Stream.fromSseWithEventDiscrimination(...)` lets you decode an SSE stream whose
payloads form a discriminated union keyed off the SSE envelope (e.g. the
`event:` line):

```java
import com.pipedream.api.core.Stream;

Stream<MyEvent> stream =
    Stream.fromSseWithEventDiscrimination(MyEvent.class, reader, "event");

for (MyEvent event : stream) {
    // ...
}
stream.close();
```

### Configurable retry status codes

`.fern/metadata.json` now exposes a `retry-status-codes` generator
configuration. The default `legacy` reproduces v1 behavior — the SDK retries on
`408`, `429`, and any `5XX`. Regenerating the SDK with `recommended` narrows the
retry set to `408`, `429`, `502`, `503`, and `504`. End users only see this
if/when the SDK is regenerated with a different value, so it is called out here
in case retry behavior changes in a future release.

## Migration checklist

- [ ] Bump `com.pipedream:pipedream` to `2.0.0` in `build.gradle` / `pom.xml`.
- [ ] If you construct `ConfigurableProp` via `ConfigurableProp.<variant>(...)`
      factories or implement `ConfigurableProp.Visitor`, switch to the
      overloaded `of(...)` factories and overloaded `visit(...)` methods.
      Replace any `prop.isApp()` / `prop.getApp()`-style checks with an
      `instanceof` check on `prop.get()` or with a visitor.
- [ ] If you call `ConfiguredPropValue.of(Object)`, switch to a typed factory or
      wrap with `ConfiguredPropValueAny.of(...)`. If you implement
      `ConfiguredPropValue.Visitor`, replace `visit(Object)` with
      `visit(ConfiguredPropValueAny)`.
- [ ] If you reference `com.pipedream.api.core.pagination.CustomPager`,
      `BiDirectionalPage`, or `AsyncCustomPager` directly, port the code to the
      standard `SyncPagingIterable<T>` iteration patterns.
- [ ] Optional: switch from `BaseClient.withToken(...)` /
      `BaseClient.withCredentials(...)` to the fluent
      `BaseClient.builder().token(...)` / `.credentials(...)` style.
- [ ] Optional: opt into the new built-in logging via `.logging(LogConfig)`.
- [ ] Recompile your project against v2 and run your test suite to surface
      anything missed.
