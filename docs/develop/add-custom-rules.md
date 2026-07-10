# Add custom rules

Both match rule and change rule can be added by yourself with our API.

## Add as dependency <a href="#user-content-get-shop-object" id="user-content-get-shop-object"></a>

{% hint style="info" %}
As of January 17, 2026, the latest plugin version number is **3.0.7**. If this date is too far away, then you should check the latest plugin version number yourself, as the provided plugin version may be outdated or unavailable.
{% endhint %}

```xml
<repositories>
    <repository>
        <id>repo-lanink-cn</id>
        <url>https://repo.lanink.cn/repository/maven-public/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>cn.superiormc.mythicchanger</groupId>
        <artifactId>plugin</artifactId>
        <version>[PLUGIN VERSION]</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

```graphql
repositories {
    maven {
        url "https://repo.lanink.cn/repository/maven-public/"
    }
}

dependencies {
    compileOnly group: 'cn.superiormc.mythicchanger', name: 'plugin', version: '[PLUGIN VERSION]'
}

```

```kts
repositories {
    maven("https://repo.lanink.cn/repository/maven-public/")
}

dependencies {
    compileOnly("cn.superiormc.mythicchanger:plugin:[PLUGIN VERSION]")
}
```

## How to do that? <a href="#how-to-do-that" id="how-to-do-that"></a>

First you need creata a new class that extends AbstractChangesRule or AbstractMatchItemRule abstract class.

Then use:

```java
ChangesManager.changersManager.registerNewRule(AbstractChangesRule rule)
```

```java
MatchItemManager.matchItemManager.registerNewRule(AbstractMatchItemRule rule)
```

depends on that rule you want to register.

Both AbstractChangeRule and AbstractMatchItem object are abstract class.

## Want me help you add?

If you don't have any plugin development experience and want me to help you, first you need consider buy the premium version of MythicChanger.

If your wanted rule is universal (means many people also want use it) and does not very complex, I **MAYBE** consider add it.&#x20;
