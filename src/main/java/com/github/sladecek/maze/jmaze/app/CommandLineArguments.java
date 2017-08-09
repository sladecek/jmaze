package com.github.sladecek.maze.jmaze.app;

import com.github.sladecek.maze.jmaze.properties.MazeProperties;

/**
 * Parses command line arguments.
 */
class CommandLineArguments {

    public CommandLineArguments(String[] args) {
        this.args = args;
    }

    public void parseArguments() {
        hasErrors = true;

        if (args.length == 0) {
            return;
        }

        if (args[0].equals("--help")) {
            isUsageRequired = true;
            hasErrors = false;
            return;
        }

        if (args[0].startsWith("--")) {
            hasErrors = true;
            return;
        }

        mazeType = args[0];

        if (args.length == 1) {
            hasErrors = false;
            return;
        }

        if (args[1].equals("--list")) {
            isPropertiesListRequired = true;
            hasErrors = false;
            return;
        }

        for (int i = 1; i < args.length; i++) {
            int eqs=args[i].indexOf("=");
            if (eqs < 1) {
                return;
            }

            String key = args[i].substring(0, eqs);
            String value = args[i].substring(eqs+1);

            if (key.isEmpty() || value.isEmpty()) {
                return;
            }
            properties.put(key, value);
        }
        hasErrors = false;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public boolean isUsageRequired() {
        return isUsageRequired;
    }

    public String getMazeType() {
        return mazeType;
    }

    public boolean isPropertiesListRequired() {
        return isPropertiesListRequired;
    }

    public MazeProperties getProperties() {
        return properties;
    }

    public String getUsage() {
        return  "maze --help\n" +
                "maze type --list\n"+
                "maze type { property==value }\n"+
                "\n"+
                "type is one of 'rect', 'circular', 'hexagonal', 'moebius', 'egg', 'triangular', 'voronoi'\n"

                ;
    }

    private final String[] args;

    private final MazeProperties properties = new MazeProperties();
    private boolean isPropertiesListRequired = false;
    private String mazeType;
    private boolean hasErrors = true;
    private boolean isUsageRequired = false;
}
