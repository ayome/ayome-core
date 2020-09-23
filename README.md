<h2 align="center">
  <img width="80" src="https://i.imgur.com/K9IsxK6.png" alt="logo"/><br>
  Ayome
</h2>
<p align="center">
  A cross platform server-management-system for minecraft on top of docker
</p>

**🧪 Not finished yet and currently not under development**

### 🔮 Vision

A web-based system to manage minecraft servers in a bungeecord network. The user should have as little to set up as possible. Just run a single file, open the url, and it works without any configuration.

**It's not done yet.** Since I have no more time for the project, I have published the current version. Everything that is written in the features already works.

### 🏷 Features

# | Description
------------ | ------------
**Automatic installation** | _It will automaticly create and configure all files after running the file_
**Proxy** | _Install, manage, edit and delete the bungeecord server_
**Static** | _Install, manage, edit and delete static servers_
**Server management** | _Start/Stop/Delete, change memory, see statistics, live cpu and memory, send and receive ingame console data_

![gif-create-server](https://i.imgur.com/Avn4Dpk.gif)

![gif-first-startup](https://i.imgur.com/5Yy1o8U.gif)

---

### 🛠 Development

#### Default ports:

# | Description
------------ | ------------
**7401** | Web-UI
**7402** | Websocket server for the Web-UI
**7403** | TCP Socket server for internal communication between minecraft/proxy servers

#### Start in development:

- Open Intellij IDEA
- Add a spring run-profile
- Type ``dev``
    ![profile](https://i.imgur.com/AGdVSSa.png)
    
### 🧱 Build production version
```
./gradlew buildAll
```
