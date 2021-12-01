package dev.tumyr.model;

public class Task {
    private String label;

    public Task(String label, String description, String solution) {
        this.label = label;
        this.description = description;
        this.solution = solution;
    }

    private String description;
    private String solution;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
