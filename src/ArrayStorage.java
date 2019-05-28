/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    //private Resume[] storage;
    private int mSize;

    // ArrayStorage constructor create array storage of resumes with 10000 elements
    public ArrayStorage() {
        mSize = 0;
    }

    void clear() {
        // All not null elements in storage set at null and set mSize = 0
        for (int i = 0; i < mSize; i++) {
            storage[i] = null;
        }
        mSize = 0;
    }

    // save resume to storage becouse we track for mSize where no null elements between elements that store Resume objects
    void save(Resume r) {
        storage[mSize] = r;
        mSize++;
    }

    // Get Resume from storage with specific uuid otherwise if Resume with such uuid doesn't exist in storage return null
    Resume get(String uuid) {
        // boolean state = true;
        int count = 0;
        while (true) {
            if (storage[count] != null) {
                if (storage[count].toString() == uuid) return storage[count];
            } else if (count == mSize) return null;

            count++;
        }
    }

    // delete Resume by uuid
    void delete(String uuid) {
        Resume nextResume;
        for (int i = 0; i < mSize; i++) {
            if (uuid == storage[i].toString()) {
                if (i < mSize - 1) {
                    for (int j = i; j < mSize; j++) {
                        nextResume = storage[j + 1];
                        storage[j] = nextResume;
                    }
                    mSize--;
                    break;
                } else {
                    storage[i] = null;
                    mSize--;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[mSize];
        for (int i = 0; i < mSize; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    // How many Resume objects in storage
    int size() {
        return mSize;
    }
}
