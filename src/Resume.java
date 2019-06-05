/**
 * Initial resume class
 */
public class Resume {
    // Unique identifier
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }

    //Overriding method equals to check if fields "uuid" in resumes a same or not.
    @Override
    public boolean equals(Object obj) {
        Resume resume = (Resume) obj;
        if (resume.getUuid() == this.uuid) {
            return true;
        } else {
            return false;
        }

    }
}
