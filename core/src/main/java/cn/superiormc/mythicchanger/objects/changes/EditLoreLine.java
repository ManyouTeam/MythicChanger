package cn.superiormc.mythicchanger.objects.changes;

import cn.superiormc.mythicchanger.MythicChanger;
import cn.superiormc.mythicchanger.objects.ObjectSingleChange;
import cn.superiormc.mythicchanger.utils.CommonUtil;
import cn.superiormc.mythicchanger.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class EditLoreLine extends AbstractChangesRule {

    @Override
    public ItemStack setChange(ObjectSingleChange singleChange) {
        ConfigurationSection editSection = singleChange.section.getConfigurationSection("edit-lore-line");
        ItemMeta meta = singleChange.getItemMeta();
        if (editSection == null || !meta.hasLore()) {
            return singleChange.getItem();
        }

        EditMode mode = EditMode.parse(singleChange.getString("edit-lore-line-mode", "REPLACE"));
        if (mode == null) {
            return singleChange.getItem();
        }

        int offset = singleChange.getInt("edit-lore-line-offset", 0);
        List<String> lore = MythicChanger.methodUtil.getItemLore(meta);
        List<String> originalLore = new ArrayList<>(lore);
        List<LineEdit> edits = new ArrayList<>();

        for (Map.Entry<String, Object> entry : editSection.getValues(true).entrySet()) {
            if (entry.getValue() instanceof ConfigurationSection) {
                continue;
            }
            String contains = TextUtil.withPAPI(singleChange.parsePlaceholder(entry.getKey()), singleChange.getPlayer());
            String plainContains = TextUtil.clear(contains);
            if (plainContains.isEmpty()) {
                continue;
            }

            String replacement = entry.getValue() == null ? "" : entry.getValue().toString();
            replacement = singleChange.parsePlaceholder(replacement);
            for (int line = 0; line < originalLore.size(); line++) {
                if (!TextUtil.clear(originalLore.get(line)).contains(plainContains)) {
                    continue;
                }

                int targetLine = line + offset;
                if (targetLine < 0 || targetLine >= originalLore.size()) {
                    continue;
                }
                String parsedReplacement = CommonUtil.modifyString(singleChange.getPlayer(), replacement,
                        "original", originalLore.get(targetLine));
                edits.add(new LineEdit(targetLine, parsedReplacement));
            }
        }

        if (edits.isEmpty()) {
            return singleChange.getItem();
        }

        switch (mode) {
            case ADD:
                edits.sort(Comparator.comparingInt(LineEdit::getLine).reversed());
                for (LineEdit edit : edits) {
                    lore.add(edit.getLine() + 1, edit.getReplacement());
                }
                break;
            case REMOVE:
                Set<Integer> removeLines = new TreeSet<>(Comparator.reverseOrder());
                for (LineEdit edit : edits) {
                    removeLines.add(edit.getLine());
                }
                for (int line : removeLines) {
                    lore.remove(line);
                }
                break;
            case REPLACE:
                for (LineEdit edit : edits) {
                    lore.set(edit.getLine(), edit.getReplacement());
                }
                break;
        }

        if (lore.isEmpty()) {
            meta.setLore(null);
        } else {
            MythicChanger.methodUtil.setItemLore(meta, lore, singleChange.getPlayer());
        }
        return singleChange.setItemMeta(meta);
    }

    @Override
    public boolean configNotContains(ConfigurationSection section) {
        return section.getConfigurationSection("edit-lore-line") == null;
    }

    private enum EditMode {
        ADD,
        REMOVE,
        REPLACE;

        private static EditMode parse(String value) {
            try {
                return valueOf(value.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException | NullPointerException exception) {
                return null;
            }
        }
    }

    private static class LineEdit {

        private final int line;
        private final String replacement;

        private LineEdit(int line, String replacement) {
            this.line = line;
            this.replacement = replacement;
        }

        private int getLine() {
            return line;
        }

        private String getReplacement() {
            return replacement;
        }
    }
}
