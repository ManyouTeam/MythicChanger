package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RemoveData extends AbstractChangesRule {

    public RemoveData() {
        super();
    }

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        Object value = singleChange.get("remove-data");
        if (value instanceof Boolean && !((Boolean) value)) {
            return singleChange.getItem();
        }
        for (String dataType : getDataTypes(singleChange, value)) {
            DataComponentType type = parseDataComponentType(dataType);
            if (type != null) {
                singleChange.getItem().unsetData(type);
            }
        }
        return singleChange.getItem();
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return !section.contains("remove-data");
    }

    private List<String> getDataTypes(ObjectSingleChange singleChange, Object value) {
        List<String> result = new ArrayList<>();
        if (value instanceof List<?>) {
            result.addAll(singleChange.getStringList("remove-data"));
            return result;
        }
        if (value != null) {
            result.add(singleChange.getString("remove-data"));
        }
        return result;
    }

    private DataComponentType parseDataComponentType(String rawType) {
        if (rawType == null || rawType.isEmpty()) {
            return null;
        }
        String type = rawType.trim();
        int namespacedIndex = type.indexOf(':');
        if (namespacedIndex >= 0 && namespacedIndex + 1 < type.length()) {
            type = type.substring(namespacedIndex + 1);
        }
        type = type.replace('-', '_').toUpperCase(Locale.ENGLISH);
        try {
            Field field = DataComponentTypes.class.getField(type);
            Object value = field.get(null);
            if (value instanceof DataComponentType) {
                return (DataComponentType) value;
            }
        } catch (ReflectiveOperationException ignored) {
            // Invalid component names are ignored so other change rules can still apply.
        }
        return null;
    }
}
