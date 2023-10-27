package dk.bondegaard.team.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class PlaceholderString implements Cloneable {

    private String string;

    private List<String> placeholderNames = Lists.newArrayList();

    private List<Object> placeholderValues = Lists.newArrayList();

    public PlaceholderString(List<String> placeholderNames) {
        this.placeholderNames(placeholderNames);
    }

    public PlaceholderString(String string, String... placeholderNames) {
        this(string, Arrays.asList(placeholderNames));
    }

    public PlaceholderString(String string, List<String> placeholderNames) {
        this(placeholderNames);
        this.string = string;
    }

    public PlaceholderString string(String string) {
        this.string = string;
        return this;
    }

    public PlaceholderString placeholderNames(String... placeholderNames) {
        return this.placeholderNames(Arrays.asList(placeholderNames));
    }

    public PlaceholderString placeholderNames(List<String> placeholderNames) {
        this.placeholderNames = new ArrayList<>(placeholderNames);
        return this;
    }

    public PlaceholderString addPlaceholderNames(String... placeholderNames) {
        this.placeholderNames.addAll(Arrays.asList(placeholderNames));
        return this;
    }

    public PlaceholderString placeholderValues(Object... placeholderValues) {
        return this.placeholderValues(Arrays.asList(placeholderValues));
    }

    public PlaceholderString placeholderValues(List<Object> placeholderValues) {
        this.placeholderValues = new ArrayList<>(placeholderValues);
        return this;
    }

    public PlaceholderString addPlaceholderValues(Object... placeholderValues) {
        this.placeholderValues.addAll(Arrays.asList(placeholderValues));
        return this;
    }

    public String parse() {
        String parsedResult = this.string;

        for (Map.Entry<String, Object> placeholder : this.toPlaceholderValuesByName().entrySet())
            parsedResult = parsedResult.replace(placeholder.getKey(), String.valueOf(placeholder.getValue()));

        return parsedResult;
    }

    public List<String> parseStrings(final List<String> strings) {
        return strings.stream()
                .map(elm -> this.string(elm).parse())
                .collect(Collectors.toList());
    }

    private Map<String, Object> toPlaceholderValuesByName() {
        Map<String, Object> placeholderValuesByName = Maps.newHashMap();

        for (int i = 0; i < this.placeholderNames.size(); i++) {
            Object value = null;

            if (i < this.placeholderValues.size()) value = this.placeholderValues.get(i);

            placeholderValuesByName.put(this.placeholderNames.get(i), value);
        }

        return placeholderValuesByName;
    }

    @Override
    public PlaceholderString clone() {
        try {
            return (PlaceholderString) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
