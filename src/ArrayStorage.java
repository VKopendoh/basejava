/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    //private Resume[] storage;
    private int size;

    void clear() {
        // All not null elements in storage set at null and set mSize = 0
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    // save resume to storage becouse we track for mSize where no null elements between elements that store Resume objects
    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    // Get Resume from storage with specific uuid otherwise if Resume with such uuid doesn't exist in storage return null
    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString() == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    // delete Resume by uuid
    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid == storage[i].toString()) {
                if (i < size - 1) {
                    for (int j = i; j < size; j++) {
                        storage[j] = storage[j + 1];
                    }
                    size--;
                    break;
                } else {
                    storage[i] = null;
                    size--;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    // How many Resume objects in storage
    int size() {
        return size;
    }
}
