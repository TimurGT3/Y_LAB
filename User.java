import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User {
    private String username;
    String password;
    private List<MeterReading> meterReadings;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.meterReadings = new ArrayList<>();
    }

    public void addMeterReading(MeterReading reading) {
        meterReadings.add(reading);
    }

    public List<MeterReading> getMeterReadings() {
        return meterReadings;
    }
}

class MeterReading {
    private int month;
    private int year;
    private Map<String, Double> readings;

    public MeterReading(int month, int year, Map<String, Double> readings) {
        this.month = month;
        this.year = year;
        this.readings = readings;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Map<String, Double> getReadings() {
        return readings;
    }
}

class UserService {
    private Map<String, User> users;
    private User currentUser;

    public UserService() {
        this.users = new HashMap<>();
        this.currentUser = null;
    }

    public void registerUser(String username, String password) {
        User newUser = new User(username, password);
        users.put(username, newUser);
    }

    public boolean loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.password.equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logoutUser() {
        currentUser = null;
    }

    public void addMeterReading(int month, int year, Map<String, Double> readings) {
        if (currentUser != null) {
            MeterReading newReading = new MeterReading(month, year, readings);
            currentUser.addMeterReading(newReading);
        }
    }

    public List<MeterReading> getLatestMeterReadings() {
        if (currentUser != null) {
            return currentUser.getMeterReadings();
        }
        return new ArrayList<>();
    }

    public List<MeterReading> getMeterReadingsForMonth(int month, int year) {
        List<MeterReading> result = new ArrayList<>();
        if (currentUser != null) {
            for (MeterReading reading : currentUser.getMeterReadings()) {
                if (reading.getMonth() == month && reading.getYear() == year) {
                    result.add(reading);
                }
            }
        }
        return result;
    }

    public List<MeterReading> getMeterReadingHistory() {
        if (currentUser != null) {
            return currentUser.getMeterReadings();
        }
        return new ArrayList<>();
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }
}