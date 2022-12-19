package models.view_models.subject;

public class SubjectCreateRequest {
    private String subjectId;
    private String subjectName;
    private int creditsNo;
    private int periodsNo;
    private String deleted;

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCreditsNo() {
        return creditsNo;
    }

    public void setCreditsNo(int creditsNo) {
        this.creditsNo = creditsNo;
    }

    public int getPeriodsNo() {
        return periodsNo;
    }

    public void setPeriodsNo(int periodsNo) {
        this.periodsNo = periodsNo;
    }
}
