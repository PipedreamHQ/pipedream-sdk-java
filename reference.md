# Reference
## AppCategories
<details><summary><code>client.appCategories.list() -> List&lt;AppCategory&gt;</code></summary>
<dl>
<dd>

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
<details><summary><code>client.apps.list() -> ListAppsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.apps().list(
    AppsListRequest
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
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.apps.retrieve(appId) -> GetAppResponse</code></summary>
<dl>
<dd>

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
<details><summary><code>client.accounts.list(projectId) -> ListAccountsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().list(
    "project_id",
    AccountsListRequest
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**appId:** `Optional<String>` — The app slug or ID to filter accounts by.
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().create(
    "project_id",
    CreateAccountOpts
        .builder()
        .appSlug("app_slug")
        .cfmapJson("cfmap_json")
        .connectToken("connect_token")
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**appId:** `Optional<String>` — The app slug or ID to filter accounts by.
    
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
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.retrieve(projectId, accountId) -> Account</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().retrieve(
    "project_id",
    "account_id",
    AccountsRetrieveRequest
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().delete("project_id", "account_id");
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().deleteByApp("project_id", "app_id");
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

## Users
<details><summary><code>client.users.deleteExternalUser(projectId, externalUserId)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().deleteExternalUser("project_id", "external_user_id");
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

## Components
<details><summary><code>client.components.list(projectId) -> GetComponentsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().list(
    "project_id",
    ComponentsListRequest
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.components.retrieve(projectId, componentId) -> GetComponentResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().retrieve("project_id", "component_id");
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**componentId:** `String` — The key that uniquely identifies the component (e.g., 'slack-send-message')
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().configureProp(
    "project_id",
    ComponentsConfigurePropRequest
        .builder()
        .body(
            ConfigurePropOpts
                .builder()
                .id("id")
                .externalUserId("external_user_id")
                .propName("prop_name")
                .build()
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**asyncHandle:** `Optional<String>` 
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().reloadProps(
    "project_id",
    ComponentsReloadPropsRequest
        .builder()
        .body(
            ReloadPropsOpts
                .builder()
                .id("id")
                .externalUserId("external_user_id")
                .build()
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**asyncHandle:** `Optional<String>` 
    
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
<details><summary><code>client.actions.list(projectId) -> GetComponentsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().list(
    "project_id",
    ActionsListRequest
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.retrieve(projectId, componentId) -> GetComponentResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().retrieve("project_id", "component_id");
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**componentId:** `String` — The key that uniquely identifies the component (e.g., 'slack-send-message')
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().configureProp(
    "project_id",
    ActionsConfigurePropRequest
        .builder()
        .body(
            ConfigurePropOpts
                .builder()
                .id("id")
                .externalUserId("external_user_id")
                .propName("prop_name")
                .build()
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**asyncHandle:** `Optional<String>` 
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().reloadProps(
    "project_id",
    ActionsReloadPropsRequest
        .builder()
        .body(
            ReloadPropsOpts
                .builder()
                .id("id")
                .externalUserId("external_user_id")
                .build()
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**asyncHandle:** `Optional<String>` 
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().run(
    "project_id",
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**asyncHandle:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**id:** `String` — The action component ID
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID
    
</dd>
</dl>

<dl>
<dd>

**configuredProps:** `Optional<Map<String, Object>>` — The configured properties for the action
    
</dd>
</dl>

<dl>
<dd>

**dynamicPropsId:** `Optional<String>` — The ID for dynamic props
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Triggers
<details><summary><code>client.triggers.list(projectId) -> GetComponentsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.triggers().list(
    "project_id",
    TriggersListRequest
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.triggers.retrieve(projectId, componentId) -> GetComponentResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.triggers().retrieve("project_id", "component_id");
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**componentId:** `String` — The key that uniquely identifies the component (e.g., 'slack-send-message')
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.triggers().configureProp(
    "project_id",
    TriggersConfigurePropRequest
        .builder()
        .body(
            ConfigurePropOpts
                .builder()
                .id("id")
                .externalUserId("external_user_id")
                .propName("prop_name")
                .build()
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**asyncHandle:** `Optional<String>` 
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.triggers().reloadProps(
    "project_id",
    TriggersReloadPropsRequest
        .builder()
        .body(
            ReloadPropsOpts
                .builder()
                .id("id")
                .externalUserId("external_user_id")
                .build()
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**asyncHandle:** `Optional<String>` 
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.triggers().deploy(
    "project_id",
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**id:** `String` — The trigger component ID
    
</dd>
</dl>

<dl>
<dd>

**externalUserId:** `String` — The external user ID
    
</dd>
</dl>

<dl>
<dd>

**configuredProps:** `Optional<Map<String, Object>>` — The configured properties for the trigger
    
</dd>
</dl>

<dl>
<dd>

**dynamicPropsId:** `Optional<String>` — The ID for dynamic props
    
</dd>
</dl>

<dl>
<dd>

**webhookUrl:** `Optional<String>` — Optional webhook URL to receive trigger events
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## DeployedTriggers
<details><summary><code>client.deployedTriggers.list(projectId) -> GetTriggersResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().list(
    "project_id",
    DeployedTriggersListRequest
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deployedTriggers.retrieve(projectId, triggerId) -> GetTriggerResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().retrieve(
    "project_id",
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().update(
    "project_id",
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

**configuredProps:** `Optional<Map<String, Object>>` — The configured properties for the trigger
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — The name of the trigger
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().delete(
    "project_id",
    "trigger_id",
    DeployedTriggersDeleteRequest
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().listEvents(
    "project_id",
    "trigger_id",
    DeployedTriggersListEventsRequest
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().listWorkflows(
    "project_id",
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().updateWorkflows(
    "project_id",
    "trigger_id",
    UpdateTriggerWorkflowsOpts
        .builder()
        .externalUserId("external_user_id")
        .workflowIds(
            new ArrayList<String>(
                Arrays.asList("workflow_ids")
            )
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().listWebhooks(
    "project_id",
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deployedTriggers().updateWebhooks(
    "project_id",
    "trigger_id",
    UpdateTriggerWebhooksOpts
        .builder()
        .externalUserId("external_user_id")
        .webhookUrls(
            new ArrayList<String>(
                Arrays.asList("webhook_urls")
            )
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

## Projects
<details><summary><code>client.projects.retrieveInfo(projectId) -> ProjectInfoResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.projects().retrieveInfo("project_id");
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Proxy
<details><summary><code>client.proxy.get(projectId, url64) -> Object</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.proxy().get(
    "project_id",
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

<details><summary><code>client.proxy.post(projectId, url64, request) -> Object</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.proxy().post(
    "project_id",
    "url_64",
    ProxyPostRequest
        .builder()
        .externalUserId("external_user_id")
        .accountId("account_id")
        .body(
            new HashMap<String, Object>() {{
                put("key", "value");
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

<details><summary><code>client.proxy.put(projectId, url64, request) -> Object</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.proxy().put(
    "project_id",
    "url_64",
    ProxyPutRequest
        .builder()
        .externalUserId("external_user_id")
        .accountId("account_id")
        .body(
            new HashMap<String, Object>() {{
                put("key", "value");
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

<details><summary><code>client.proxy.delete(projectId, url64) -> Object</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.proxy().delete(
    "project_id",
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

<details><summary><code>client.proxy.patch(projectId, url64, request) -> Object</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.proxy().patch(
    "project_id",
    "url_64",
    ProxyPatchRequest
        .builder()
        .externalUserId("external_user_id")
        .accountId("account_id")
        .body(
            new HashMap<String, Object>() {{
                put("key", "value");
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tokens().create(
    "project_id",
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
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

**externalUserId:** `String` — Your end user ID, for whom you're creating the token
    
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
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.tokens.validate(projectId, ctok) -> ValidateTokenResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tokens().validate(
    "project_id",
    "ctok",
    TokensValidateRequest
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

**projectId:** `String` — The project ID, which starts with 'proj_'.
    
</dd>
</dl>

<dl>
<dd>

**ctok:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**params:** `Optional<ValidateTokenParams>` 
    
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

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.oauthTokens().create(
    CreateOAuthTokenOpts
        .builder()
        .grantType("client_credentials")
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
</dd>
</dl>


</dd>
</dl>
</details>
