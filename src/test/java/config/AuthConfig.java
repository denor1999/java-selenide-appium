package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:browserstack.properties"
})
public interface AuthConfig extends Config {

    @Key("browserstack.user")
    String username();

    @Key("browserstack.key")
    String key();
}
