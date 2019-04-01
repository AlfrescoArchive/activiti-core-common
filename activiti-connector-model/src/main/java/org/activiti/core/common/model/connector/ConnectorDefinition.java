package org.activiti.core.common.model.connector;

import java.util.Map;

public class ConnectorDefinition {

    private String name;

    private String description;

    private String icon;

    private Map<String, ActionDefinition> actions;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public Map<String, ActionDefinition> getActions() {
        return actions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setActions(Map<String, ActionDefinition> actions) {
        this.actions = actions;
    }
}
