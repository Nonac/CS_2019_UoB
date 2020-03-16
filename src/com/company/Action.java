
import java.util.ArrayList;

public class Action {
    private ArrayList<String> trigger;
    private ArrayList<String> subjects;
    private ArrayList<String> consumed;
    private ArrayList<String> produced;
    private ArrayList<String> narration;

    public Action() {
        this.trigger = new ArrayList<>();
        this.consumed = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.produced = new ArrayList<>();
        this.narration = new ArrayList<>();
    }

    public void setNarration(String narration) {
        this.narration.add(narration);
    }

    public void setTrigger(String trigger) {
        this.trigger.add(trigger);
    }

    public void setSubjects(String subjects) {
        this.subjects.add(subjects);
    }

    public void setConsumed(String consumed) {
        this.consumed.add(consumed);
    }

    public void setProduced(String produced) {
        this.produced.add(produced);
    }

    public ArrayList<String> getConsumed() {
        return consumed;
    }

    public ArrayList<String> getProduced() {
        return produced;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public ArrayList<String> getTrigger() {
        return trigger;
    }

    public ArrayList<String> getNarration() {
        return narration;
    }
}
