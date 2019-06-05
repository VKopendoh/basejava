/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    private int size;

    public void clear() {
        // All not null elements in storage set at null and set mSize = 0
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    // save resume to storage becouse we track for size where no null elements between elements that store Resume objects
    public void save(Resume r) {
        // check if resume don't exist, save it to storage, otherwise print that it already exist. Also check for array overflow.
        if (!isResumeExist(r) && size < 10000) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("Error: Can't save, resume with uuid: " + r.getUuid() + " already exist or array overflow.");
        }

    }

    // Get Resume from storage with specific uuid otherwise if Resume with such uuid doesn't exist in storage return null
    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString() == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    // delete Resume by uuid
    public void delete(String uuid) {
        // Check if resume exist, delete it, otherwise print massage that it's not.
        boolean exist = false;
        for (int i = 0; i < size; i++) {
            if (uuid == storage[i].toString()) {
                exist = true;
            }
        }

        if (exist) {
            for (int i = 0; i < size; i++) {
                if (uuid == storage[i].toString()) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                }
            }
        } else {
            System.out.println("Error: Can't delete, resume with uuid: " + uuid + " not exist.");
        }

    }

    public void update(Resume resume) {
        //If resume exist update it else print massage that it's not.
        if (isResumeExist(resume)) {
            for (int i = 0; i < size; i++) {
                if (resume.equals(storage[i])) {
                    storage[i] = resume;
                }
            }
        } else {
            System.out.println("Error: Can't update, resume with uuid: " + resume.getUuid() + " not exist.");
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    // How many Resume objects in storage
    public int size() {
        return size;
    }

    // Check if resume exist or not in storage
    public boolean isResumeExist(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (resume.equals(storage[i])) {
                return true;
            }
        }
        return false;
    }

}
