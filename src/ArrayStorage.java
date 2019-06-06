import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int STORAGE_SIZE = 10_000;
    private Resume[] storage = new Resume[STORAGE_SIZE];

    private int size;

    // Clear storage
    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    // save resume to storage because we track for size where no null elements between elements that store Resume objects
    public void save(Resume resume) {
        if (size > storage.length) {
            System.out.println("Error: Can't save, resume with uuid: " + resume.getUuid() + "  array overflow.");
            return;
        }
        if (isResumeExist(resume.getUuid()) == -1) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Error: Can't save, resume with uuid: " + resume.getUuid() + " already exist.");
        }

    }

    // Get Resume from storage with specific uuid otherwise if Resume with such uuid doesn't exist in storage return null
    public Resume get(String uuid) {
        int i = isResumeExist(uuid);
        if (i > -1) {
            return storage[i];
        }
        System.out.println("Error: Can't get, resume with uuid: " + uuid + " not exist.");
        return null;
    }

    // delete Resume by uuid
    public void delete(String uuid) {
        int i = isResumeExist(uuid);
        if (i > -1) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Error: Can't delete, resume with uuid: " + uuid + " not exist.");
        }
    }

    //Update resume in storage
    public void update(Resume resume) {
        int i = isResumeExist(resume.getUuid());
        if (i > -1) {
            storage[i] = resume;
        } else {
            System.out.println("Error: Can't update, resume with uuid: " + resume.getUuid() + " not exist.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    // Get how many Resume objects in storage
    public int size() {
        return size;
    }

    // Check if resume exist or not in storage
    private int isResumeExist(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

}
