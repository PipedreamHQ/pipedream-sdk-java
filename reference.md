# Reference
## AppCategories
<details><summary><code>client.appCategories.list() -> List&amp;lt;AppCategory&amp;gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all available categories for integrated apps
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.appCategories().list();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.appCategories.retrieve(id) -> AppCategory</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get details of a specific app category by its ID
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.appCategories().retrieve("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the app category to retrieve
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Apps
<details><summary><code>client.apps.list() -> SyncPagingIterable&amp;lt;App&amp;gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all available apps with optional filtering and sorting
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.apps().list(
    AppsListRequest
        .builder()
        .categoryIds(
            Arrays.asList("category_ids")
        )
        .after("after")
        .before("before")
        .limit(1)
        .q("q")
        .sortKey(AppsListRequestSortKey.NAME)
        .sortDirection(AppsListRequestSortDirection.ASC)
        .hasComponents(true)
        .hasActions(true)
        .hasTriggers(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**after:** `Optional<String>` — The cursor to start from for pagination
    
</dd>
</dl>

<dl>
<dd>

**before:** `Optional<String>` — The cursor to end before for pagination
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — The maximum number of results to return
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — A search query to filter the apps
    
</dd>
</dl>

<dl>
<dd>

**sortKey:** `Optional<AppsListRequestSortKey>` — The key to sort the apps by
    
</dd>
</dl>

<dl>
<dd>

**sortDirection:** `Optional<AppsListRequestSortDirection>` — The direction to sort the apps
    
</dd>
</dl>

<dl>
<dd>

**categoryIds:** `Optional<String>` — Only return apps in these categories
    
</dd>
</dl>

<dl>
<dd>

**hasComponents:** `Optional<Boolean>` — Only return apps that have components (actions or triggers)
    
</dd>
</dl>

<dl>
<dd>

**hasActions:** `Optional<Boolean>` — Only return apps that have actions
    
</dd>
</dl>

<dl>
<dd>

**hasTriggers:** `Optional<Boolean>` — Only return apps that have triggers
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.apps.retrieve(appId) -> GetAppResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get detailed information about a specific app by ID or name slug
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.apps().retrieve("app_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**appId:** `String` — The name slug or ID of the app (e.g., 'slack', 'github')
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Accounts
<details><summary><code>client.accounts.list(projectId) -> SyncPagingIterable&amp;lt;Account&amp;gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all connected accounts for the project with optional filtering
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().list(
    AccountsListRequest
        .builder()
        .externalUserId("external_user_id")
        .oauthAppId("oauth_app_id")
        .after("after")
        .before("before")
        .limit(1)
        .app("app")
        .includeCredentials(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**oauthAppId:** `Optional<String>` — The OAuth app ID to filter by, if applicable
    
</dd>
</dl>

<dl>
<dd>

**after:** `Optional<String>` — The cursor to start from for pagination
    
</dd>
</dl>

<dl>
<dd>

**before:** `Optional<String>` — The cursor to end before for pagination
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — The maximum number of results to return
    
</dd>
</dl>

<dl>
<dd>

**app:** `Optional<String>` — The app slug or ID to filter accounts by.
    
</dd>
</dl>

<dl>
<dd>

**includeCredentials:** `Optional<Boolean>` — Whether to retrieve the account's credentials or not
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.create(projectId, request) -> Account</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Connect a new account for an external user in the project
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().create(
    CreateAccountOpts
        .builder()
        .appSlug("app_slug")
        .cfmapJson("cfmap_json")
        .connectToken("connect_token")
        .externalUserId("external_user_id")
        .oauthAppId("oauth_app_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**oauthAppId:** `Optional<String>` — The OAuth app ID to filter by, if applicable
    
</dd>
</dl>

<dl>
<dd>

**appSlug:** `String` — The app slug for the account
    
</dd>
</dl>

<dl>
<dd>

**cfmapJson:** `String` — JSON string containing the custom fields mapping
    
</dd>
</dl>

<dl>
<dd>

**connectToken:** `String` — The connect token for authentication
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Optional name for the account
    
</dd>
</dl>

<dl>
<dd>

**accountId:** `Optional<String>` — An existing account ID to reconnect. When provided, the account's credentials are updated instead of creating a new account. Must belong to the same external user and project environment as the connect token, and match the app identified by app_slug.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.retrieve(projectId, accountId) -> Account</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get the details for a specific connected account
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().retrieve(
    "account_id",
    AccountsRetrieveRequest
        .builder()
        .includeCredentials(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**accountId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**includeCredentials:** `Optional<Boolean>` — Whether to retrieve the account's credentials or not
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.delete(projectId, accountId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove a connected account and its associated credentials
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().delete("account_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**accountId:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.deleteByApp(projectId, appId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove all connected accounts for a specific app
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().deleteByApp("app_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**appId:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.listByExternalUser(projectId, externalUserId) -> List&amp;lt;Account&amp;gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

List all connected accounts for a specific external user. Equivalent to GET /accounts with external_user_id filter but uses path-based routing.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().listByExternalUser(
    "external_user_id",
    AccountsListByExternalUserRequest
        .builder()
        .includeCredentials(true)
        .app("app")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**includeCredentials:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**app:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users
<details><summary><code>client.users.deleteExternalUser(projectId, externalUserId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove an external user and all their associated accounts and resources
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().deleteExternalUser("external_user_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.list(projectId) -> SyncPagingIterable&amp;lt;ExternalUser&amp;gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all external users for the project
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().list(
    UsersListRequest
        .builder()
        .after("after")
        .before("before")
        .limit(1)
        .q("q")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**after:** `Optional<String>` — The cursor to start from for pagination
    
</dd>
</dl>

<dl>
<dd>

**before:** `Optional<String>` — The cursor to end before for pagination
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — The maximum number of results to return
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Filter users by external_id (partial match)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Components
<details><summary><code>client.components.list(projectId) -> SyncPagingIterable&amp;lt;Component&amp;gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve available components with optional search and app filtering
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().list(
    ComponentsListRequest
        .builder()
        .after("after")
        .before("before")
        .limit(1)
        .q("q")
        .app("app")
        .registry(ComponentsListRequestRegistry.PUBLIC)
        .componentType(ComponentType.TRIGGER)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**after:** `Optional<String>` — The cursor to start from for pagination
    
</dd>
</dl>

<dl>
<dd>

**before:** `Optional<String>` — The cursor to end before for pagination
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — The maximum number of results to return
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — A search query to filter the components
    
</dd>
</dl>

<dl>
<dd>

**app:** `Optional<String>` — The ID or name slug of the app to filter the components
    
</dd>
</dl>

<dl>
<dd>

**registry:** `Optional<ComponentsListRequestRegistry>` — The registry to retrieve components from. Defaults to 'all' ('public', 'private', or 'all')
    
</dd>
</dl>

<dl>
<dd>

**componentType:** `Optional<ComponentType>` — The type of the component to filter the components
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.components.retrieve(projectId, componentId) -> GetComponentResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get detailed configuration for a specific component by its key
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().retrieve(
    "component_id",
    ComponentsRetrieveRequest
        .builder()
        .version("1.2.3")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**componentId:** `String` — The key that uniquely identifies the component (e.g., 'slack-send-message')
    
</dd>
</dl>

<dl>
<dd>

**version:** `Optional<String>` — Optional semantic version of the component to retrieve (for example '1.0.0')
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.components.configureProp(projectId, request) -> ConfigurePropResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve remote options for a given prop for a component
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().configureProp(
    ConfigurePropOpts
        .builder()
        .id("id")
        .externalUserId("external_user_id")
        .propName("prop_name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**request:** `ConfigurePropOpts` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.components.reloadProps(projectId, request) -> ReloadPropsResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Reload the prop definition based on the currently configured props
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().reloadProps(
    ReloadPropsOpts
        .builder()
        .id("id")
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**request:** `ReloadPropsOpts` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Actions
<details><summary><code>client.actions.list(projectId) -> SyncPagingIterable&amp;lt;Component&amp;gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve available actions with optional search and app filtering
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().list(
    ActionsListRequest
        .builder()
        .after("after")
        .before("before")
        .limit(1)
        .q("q")
        .app("app")
        .registry(ActionsListRequestRegistry.PUBLIC)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**after:** `Optional<String>` — The cursor to start from for pagination
    
</dd>
</dl>

<dl>
<dd>

**before:** `Optional<String>` — The cursor to end before for pagination
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — The maximum number of results to return
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — A search query to filter the actions
    
</dd>
</dl>

<dl>
<dd>

**app:** `Optional<String>` — The ID or name slug of the app to filter the actions
    
</dd>
</dl>

<dl>
<dd>

**registry:** `Optional<ActionsListRequestRegistry>` — The registry to retrieve actions from. Defaults to 'all' ('public', 'private', or 'all')
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.retrieve(projectId, componentId) -> GetComponentResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get detailed configuration for a specific action by its key
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().retrieve(
    "component_id",
    ActionsRetrieveRequest
        .builder()
        .version("1.2.3")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**componentId:** `String` — The key that uniquely identifies the component (e.g., 'slack-send-message')
    
</dd>
</dl>

<dl>
<dd>

**version:** `Optional<String>` — Optional semantic version of the component to retrieve (for example '1.0.0')
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.configureProp(projectId, request) -> ConfigurePropResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve remote options for a given prop for a action
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().configureProp(
    ConfigurePropOpts
        .builder()
        .id("id")
        .externalUserId("external_user_id")
        .propName("prop_name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**request:** `ConfigurePropOpts` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.reloadProps(projectId, request) -> ReloadPropsResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Reload the prop definition based on the currently configured props
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().reloadProps(
    ReloadPropsOpts
        .builder()
        .id("id")
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**request:** `ReloadPropsOpts` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.run(projectId, request) -> RunActionResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Execute an action with the provided configuration and return results
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().run(
    RunActionOpts
        .builder()
        .id("id")
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**id:** `String` — The action component ID
    
</dd>
</dl>

<dl>
<dd>

**version:** `Optional<String>` — Optional action component version (in SemVer format, for example '1.0.0'), defaults to latest
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID
    
</dd>
</dl>

<dl>
<dd>

**configuredProps:** `Optional<Map<String, ConfiguredPropValue>>` 
    
</dd>
</dl>

<dl>
<dd>

**dynamicPropsId:** `Optional<String>` — The ID for dynamic props
    
</dd>
</dl>

<dl>
<dd>

**stashId:** `Optional<RunActionOptsStashId>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Triggers
<details><summary><code>client.triggers.list(projectId) -> SyncPagingIterable&amp;lt;Component&amp;gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve available triggers with optional search and app filtering
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.triggers().list(
    TriggersListRequest
        .builder()
        .after("after")
        .before("before")
        .limit(1)
        .q("q")
        .app("app")
        .registry(TriggersListRequestRegistry.PUBLIC)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**after:** `Optional<String>` — The cursor to start from for pagination
    
</dd>
</dl>

<dl>
<dd>

**before:** `Optional<String>` — The cursor to end before for pagination
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — The maximum number of results to return
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — A search query to filter the triggers
    
</dd>
</dl>

<dl>
<dd>

**app:** `Optional<String>` — The ID or name slug of the app to filter the triggers
    
</dd>
</dl>

<dl>
<dd>

**registry:** `Optional<TriggersListRequestRegistry>` — The registry to retrieve triggers from. Defaults to 'all' ('public', 'private', or 'all')
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.triggers.retrieve(projectId, componentId) -> GetComponentResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get detailed configuration for a specific trigger by its key
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.triggers().retrieve(
    "component_id",
    TriggersRetrieveRequest
        .builder()
        .version("1.2.3")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**componentId:** `String` — The key that uniquely identifies the component (e.g., 'slack-send-message')
    
</dd>
</dl>

<dl>
<dd>

**version:** `Optional<String>` — Optional semantic version of the component to retrieve (for example '1.0.0')
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.triggers.configureProp(projectId, request) -> ConfigurePropResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve remote options for a given prop for a trigger
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.triggers().configureProp(
    ConfigurePropOpts
        .builder()
        .id("id")
        .externalUserId("external_user_id")
        .propName("prop_name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**request:** `ConfigurePropOpts` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.triggers.reloadProps(projectId, request) -> ReloadPropsResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Reload the prop definition based on the currently configured props
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.triggers().reloadProps(
    ReloadPropsOpts
        .builder()
        .id("id")
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**request:** `ReloadPropsOpts` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.triggers.deploy(projectId, request) -> DeployTriggerResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Deploy a trigger to listen for and emit events
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.triggers().deploy(
    DeployTriggerOpts
        .builder()
        .id("id")
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**id:** `String` — The trigger component ID
    
</dd>
</dl>

<dl>
<dd>

**version:** `Optional<String>` — Optional trigger component version (in SemVer format, for example '1.0.0'), defaults to latest
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID
    
</dd>
</dl>

<dl>
<dd>

**configuredProps:** `Optional<Map<String, ConfiguredPropValue>>` 
    
</dd>
</dl>

<dl>
<dd>

**dynamicPropsId:** `Optional<String>` — The ID for dynamic props
    
</dd>
</dl>

<dl>
<dd>

**workflowId:** `Optional<String>` — Optional ID of a workflow to receive trigger events
    
</dd>
</dl>

<dl>
<dd>

**webhookUrl:** `Optional<String>` — Optional webhook URL to receive trigger events
    
</dd>
</dl>

<dl>
<dd>

**emitOnDeploy:** `Optional<Boolean>` — Whether the trigger should emit events during the deploy hook execution. Defaults to true if not specified.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## DeployedTriggers
<details><summary><code>client.deployedTriggers.list(projectId) -> SyncPagingIterable&amp;lt;Emitter&amp;gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all deployed triggers for a specific external user
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().list(
    DeployedTriggersListRequest
        .builder()
        .externalUserId("external_user_id")
        .after("after")
        .before("before")
        .limit(1)
        .emitterType(EmitterType.EMAIL)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**after:** `Optional<String>` — The cursor to start from for pagination
    
</dd>
</dl>

<dl>
<dd>

**before:** `Optional<String>` — The cursor to end before for pagination
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — The maximum number of results to return
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — Your end user ID, for whom you deployed the trigger
    
</dd>
</dl>

<dl>
<dd>

**emitterType:** `Optional<EmitterType>` — Filter deployed triggers by emitter type (defaults to 'source' if not provided)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deployedTriggers.retrieve(projectId, triggerId) -> GetTriggerResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get details of a specific deployed trigger by its ID
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().retrieve(
    "trigger_id",
    DeployedTriggersRetrieveRequest
        .builder()
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — Your end user ID, for whom you deployed the trigger
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deployedTriggers.update(projectId, triggerId, request) -> GetTriggerResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Modify the configuration of a deployed trigger, including active status
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().update(
    "trigger_id",
    UpdateTriggerOpts
        .builder()
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID who owns the trigger
    
</dd>
</dl>

<dl>
<dd>

**active:** `Optional<Boolean>` — Whether the trigger should be active
    
</dd>
</dl>

<dl>
<dd>

**configuredProps:** `Optional<Map<String, ConfiguredPropValue>>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — The name of the trigger
    
</dd>
</dl>

<dl>
<dd>

**emitOnDeploy:** `Optional<Boolean>` — Whether the trigger should emit events during deployment
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deployedTriggers.delete(projectId, triggerId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove a deployed trigger and stop receiving events
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().delete(
    "trigger_id",
    DeployedTriggersDeleteRequest
        .builder()
        .externalUserId("external_user_id")
        .ignoreHookErrors(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID who owns the trigger
    
</dd>
</dl>

<dl>
<dd>

**ignoreHookErrors:** `Optional<Boolean>` — Whether to ignore errors during deactivation hook
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deployedTriggers.listEvents(projectId, triggerId) -> GetTriggerEventsResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve recent events emitted by a deployed trigger
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().listEvents(
    "trigger_id",
    DeployedTriggersListEventsRequest
        .builder()
        .externalUserId("external_user_id")
        .n(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — Your end user ID, for whom you deployed the trigger
    
</dd>
</dl>

<dl>
<dd>

**n:** `Optional<Integer>` — The number of events to retrieve (defaults to 20 if not provided)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deployedTriggers.listWorkflows(projectId, triggerId) -> GetTriggerWorkflowsResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get workflows connected to receive events from this trigger
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().listWorkflows(
    "trigger_id",
    DeployedTriggersListWorkflowsRequest
        .builder()
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID who owns the trigger
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deployedTriggers.updateWorkflows(projectId, triggerId, request) -> GetTriggerWorkflowsResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Connect or disconnect workflows to receive trigger events
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().updateWorkflows(
    "trigger_id",
    UpdateTriggerWorkflowsOpts
        .builder()
        .externalUserId("external_user_id")
        .workflowIds(
            Arrays.asList("workflow_ids")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID who owns the trigger
    
</dd>
</dl>

<dl>
<dd>

**workflowIds:** `List<String>` — Array of workflow IDs to set
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deployedTriggers.listWebhooks(projectId, triggerId) -> GetTriggerWebhooksResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get webhook URLs configured to receive trigger events
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().listWebhooks(
    "trigger_id",
    DeployedTriggersListWebhooksRequest
        .builder()
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID who owns the trigger
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deployedTriggers.updateWebhooks(projectId, triggerId, request) -> GetTriggerWebhooksResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Configure webhook URLs to receive trigger events. `signing_key` is only returned for OAuth-authenticated requests.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().updateWebhooks(
    "trigger_id",
    UpdateTriggerWebhooksOpts
        .builder()
        .externalUserId("external_user_id")
        .webhookUrls(
            Arrays.asList("webhook_urls")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID who owns the trigger
    
</dd>
</dl>

<dl>
<dd>

**webhookUrls:** `List<String>` — Array of webhook URLs to set
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deployedTriggers.retrieveWebhook(projectId, triggerId, webhookId) -> GetWebhookWithSigningKeyResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a specific webhook for a deployed trigger, including its signing key
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().retrieveWebhook(
    "trigger_id",
    "webhook_id",
    DeployedTriggersRetrieveWebhookRequest
        .builder()
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**webhookId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID who owns the trigger
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deployedTriggers.regenerateWebhookSigningKey(projectId, triggerId, webhookId) -> GetWebhookWithSigningKeyResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Regenerate the signing key for a specific webhook on a deployed trigger
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().regenerateWebhookSigningKey(
    "trigger_id",
    "webhook_id",
    DeployedTriggersRegenerateWebhookSigningKeyRequest
        .builder()
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**webhookId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID who owns the trigger
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## ProjectEnvironment
<details><summary><code>client.projectEnvironment.retrieveWebhook(projectId) -> GetWebhookResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve the webhook configured for a project environment
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projectEnvironment().retrieveWebhook();
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.projectEnvironment.updateWebhook(projectId, request) -> SetWebhookResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create or update the webhook URL for a project environment. Creating a webhook returns `signing_key`; updating an existing webhook does not.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projectEnvironment().updateWebhook(
    SetWebhookOpts
        .builder()
        .url("url")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**url:** `String` — The webhook URL to set
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.projectEnvironment.deleteWebhook(projectId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove the webhook configured for a project environment
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projectEnvironment().deleteWebhook();
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.projectEnvironment.regenerateWebhookSigningKey(projectId) -> GetWebhookWithSigningKeyResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Regenerate the signing key for the project environment webhook
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projectEnvironment().regenerateWebhookSigningKey();
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Projects
<details><summary><code>client.projects.list() -> SyncPagingIterable&amp;lt;Project&amp;gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

List the projects that are available to the authenticated Connect client
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projects().list(
    ProjectsListRequest
        .builder()
        .after("after")
        .before("before")
        .limit(1)
        .q("q")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**after:** `Optional<String>` — The cursor to start from for pagination
    
</dd>
</dl>

<dl>
<dd>

**before:** `Optional<String>` — The cursor to end before for pagination
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — The maximum number of results to return
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — A search query to filter the projects
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.projects.create(request) -> Project</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a new project for the authenticated workspace
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projects().create(
    CreateProjectOpts
        .builder()
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` — Name of the project
    
</dd>
</dl>

<dl>
<dd>

**appName:** `Optional<String>` — Display name for the Connect application
    
</dd>
</dl>

<dl>
<dd>

**supportEmail:** `Optional<String>` — Support email displayed to end users
    
</dd>
</dl>

<dl>
<dd>

**connectRequireKeyAuthTest:** `Optional<Boolean>` — Send a test request to the upstream API when adding Connect accounts for key-based apps
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.projects.retrieve(projectId) -> Project</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get the project details for a specific project
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projects().retrieve("project_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.projects.delete(projectId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a project owned by the authenticated workspace
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projects().delete("project_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.projects.update(projectId, request) -> Project</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update project details or application information
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projects().update(
    "project_id",
    UpdateProjectOpts
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Name of the project
    
</dd>
</dl>

<dl>
<dd>

**appName:** `Optional<String>` — Display name for the Connect application
    
</dd>
</dl>

<dl>
<dd>

**supportEmail:** `Optional<String>` — Support email displayed to end users
    
</dd>
</dl>

<dl>
<dd>

**connectRequireKeyAuthTest:** `Optional<Boolean>` — Send a test request to the upstream API when adding Connect accounts for key-based apps
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.projects.updateLogo(projectId, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Upload or replace the project logo
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projects().updateLogo(
    "project_id",
    UpdateProjectLogoOpts
        .builder()
        .logo("data:image/png;base64,AAAAAA...")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**logo:** `String` — Data URI containing the new Base64 encoded image
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.projects.retrieveInfo(projectId) -> ProjectInfoResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve project configuration and environment details
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projects().retrieveInfo();
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## FileStash
<details><summary><code>client.fileStash.downloadFile(projectId) -> InputStream</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Download a file from File Stash
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.fileStash().downloadFile(
    FileStashDownloadFileRequest
        .builder()
        .s3Key("s3_key")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**s3Key:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Proxy
<details><summary><code>client.proxy.get(projectId, url64) -> InputStream</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Forward an authenticated GET request to an external API using an external user's account credentials
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.proxy().get(
    "url_64",
    ProxyGetRequest
        .builder()
        .externalUserId("external_user_id")
        .accountId("account_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**url64:** `String` — Base64-encoded target URL
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID for the proxy request
    
</dd>
</dl>

<dl>
<dd>

**accountId:** `String` — The account ID to use for authentication
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.proxy.post(projectId, url64, request) -> InputStream</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Forward an authenticated POST request to an external API using an external user's account credentials
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.proxy().post(
    "url_64",
    ProxyPostRequest
        .builder()
        .externalUserId("external_user_id")
        .accountId("account_id")
        .body(
            new HashMap<String, Object>() {{
                put("string", new 
                HashMap<String, Object>() {{put("key", "value");
                }});
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**url64:** `String` — Base64-encoded target URL
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID for the proxy request
    
</dd>
</dl>

<dl>
<dd>

**accountId:** `String` — The account ID to use for authentication
    
</dd>
</dl>

<dl>
<dd>

**request:** `Map<String, Object>` — Request body to forward to the target API
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.proxy.put(projectId, url64, request) -> InputStream</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Forward an authenticated PUT request to an external API using an external user's account credentials
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.proxy().put(
    "url_64",
    ProxyPutRequest
        .builder()
        .externalUserId("external_user_id")
        .accountId("account_id")
        .body(
            new HashMap<String, Object>() {{
                put("string", new 
                HashMap<String, Object>() {{put("key", "value");
                }});
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**url64:** `String` — Base64-encoded target URL
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID for the proxy request
    
</dd>
</dl>

<dl>
<dd>

**accountId:** `String` — The account ID to use for authentication
    
</dd>
</dl>

<dl>
<dd>

**request:** `Map<String, Object>` — Request body to forward to the target API
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.proxy.delete(projectId, url64) -> InputStream</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Forward an authenticated DELETE request to an external API using an external user's account credentials
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.proxy().delete(
    "url_64",
    ProxyDeleteRequest
        .builder()
        .externalUserId("external_user_id")
        .accountId("account_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**url64:** `String` — Base64-encoded target URL
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID for the proxy request
    
</dd>
</dl>

<dl>
<dd>

**accountId:** `String` — The account ID to use for authentication
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.proxy.patch(projectId, url64, request) -> InputStream</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Forward an authenticated PATCH request to an external API using an external user's account credentials
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.proxy().patch(
    "url_64",
    ProxyPatchRequest
        .builder()
        .externalUserId("external_user_id")
        .accountId("account_id")
        .body(
            new HashMap<String, Object>() {{
                put("string", new 
                HashMap<String, Object>() {{put("key", "value");
                }});
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**url64:** `String` — Base64-encoded target URL
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID for the proxy request
    
</dd>
</dl>

<dl>
<dd>

**accountId:** `String` — The account ID to use for authentication
    
</dd>
</dl>

<dl>
<dd>

**request:** `Map<String, Object>` — Request body to forward to the target API
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Tokens
<details><summary><code>client.tokens.create(projectId, request) -> CreateTokenResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Generate a Connect token to use for client-side authentication
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tokens().create(
    CreateTokenOpts
        .builder()
        .externalUserId("external_user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**projectId:** `String` — The project ID, which starts with `proj_`.
    
</dd>
</dl>

<dl>
<dd>

**allowedOrigins:** `Optional<List<String>>` — List of allowed origins for CORS
    
</dd>
</dl>

<dl>
<dd>

**errorRedirectUri:** `Optional<String>` — URI to redirect to on error
    
</dd>
</dl>

<dl>
<dd>

**expiresIn:** `Optional<Integer>` — Token TTL in seconds (max 14400 = 4 hours). Defaults to 4 hours if not specified.
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — Your end user ID, for whom you're creating the token
    
</dd>
</dl>

<dl>
<dd>

**scope:** `Optional<String>` — Space-separated scopes to restrict token permissions. Defaults to 'connect:*' if not specified. See https://pipedream.com/docs/connect/api-reference/authentication#connect-token-scopes for more information.
    
</dd>
</dl>

<dl>
<dd>

**successRedirectUri:** `Optional<String>` — URI to redirect to on success
    
</dd>
</dl>

<dl>
<dd>

**webhookUri:** `Optional<String>` — Webhook URI for notifications
    
</dd>
</dl>

<dl>
<dd>

**allowProgressiveScopes:** `Optional<Boolean>` — When true, end users may authorize a subset of the app's OAuth scopes; only the app's functional scopes (needed for the post-OAuth test request) are enforced. Defaults to false.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.tokens.validate(ctok) -> ValidateTokenResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Confirm the validity of a Connect token
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tokens().validate(
    "ctok",
    TokensValidateRequest
        .builder()
        .appId("app_id")
        .accountId("account_id")
        .oauthAppId("oauth_app_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ctok:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**appId:** `String` — The app ID to validate against
    
</dd>
</dl>

<dl>
<dd>

**accountId:** `Optional<String>` — An existing account ID to reconnect. Must belong to the app identified by app_id.
    
</dd>
</dl>

<dl>
<dd>

**oauthAppId:** `Optional<String>` — The OAuth app ID to validate against (if the token is for an OAuth app)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Usage
<details><summary><code>client.usage.list() -> ConnectUsageResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve Connect usage records for a time window
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.usage().list(
    UsageListRequest
        .builder()
        .startTs(1)
        .endTs(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**startTs:** `Integer` — Usage window start timestamp (seconds)
    
</dd>
</dl>

<dl>
<dd>

**endTs:** `Integer` — Usage window end timestamp (seconds)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## OauthTokens
<details><summary><code>client.oauthTokens.create(request) -> CreateOAuthTokenResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Exchange OAuth credentials for an access token
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.oauthTokens().create(
    CreateOAuthTokenOpts
        .builder()
        .clientId("client_id")
        .clientSecret("client_secret")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**grantType:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**clientId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**clientSecret:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**scope:** `Optional<String>` — Optional space-separated scopes for the access token. Defaults to `*`.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

