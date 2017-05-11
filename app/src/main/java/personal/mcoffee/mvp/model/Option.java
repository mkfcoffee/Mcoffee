package personal.mcoffee.mvp.model;

/**
 * Created by Mcoffee.
 */

public class Option {
    public String option;
    public String hint;
    public String result;

    public Option(String option) {
        this(option, null, null);
    }

    public Option(String option, String hint) {
        this(option, hint, null);
    }

    public Option(String option, String hint, String result) {
        this.option = option;
        this.hint = hint;
        this.result = result;
    }
}
