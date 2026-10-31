package model;

public class Complaint {

    private int complaintId;
    private int userId;
    private int categoryId;
    private int departmentId;

    private String title;
    private String description;
    private String location;
    private String priority;
    private String status;


    // Default Constructor
    public Complaint() {
    }


    // Parameterized Constructor
    public Complaint(
            int complaintId,
            int userId,
            int categoryId,
            int departmentId,
            String title,
            String description,
            String location,
            String priority,
            String status) {

        this.complaintId = complaintId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.departmentId = departmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.priority = priority;
        this.status = status;
    }


    // Complaint ID
    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }


    // User ID
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    // Category ID
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    // Department ID
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }


    // Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    // Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    // Location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    // Priority
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    // Status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}