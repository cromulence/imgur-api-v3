package net.cromulence.imgur.apiv3.datamodel.constants;

public enum ReportReason {
    DOESNT_BELONG_ON_IMGUR("1"),
    SPAM("2"),
    ABUSIVE("3"),
    NOT_MARKED_MATURE("4"),
    PORNOGRAPHY("5");

    private final String code;

    ReportReason(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
