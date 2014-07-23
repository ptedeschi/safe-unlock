package br.com.tedeschi.safeunlock.persistence.vo;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table TB_SETTINGS.
 */
public class Settings {

    private Long id;
    private Boolean enabled;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Settings() {
    }

    public Settings(Long id) {
        this.id = id;
    }

    public Settings(Long id, Boolean enabled) {
        this.id = id;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}