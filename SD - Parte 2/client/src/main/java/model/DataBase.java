package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class DataBase implements Serializable {

	    /**
	 * acho que o database ficará melhor no servidor, espero que isso ajude
	 */
	private static final long serialVersionUID = -4771087524870615263L;
		private Long id;
	    private String message;
	    private String createdBy;
	    private LocalDateTime createdAt;
	    private String updatedBy;
	    private LocalDateTime updatedAt;

	    public DataBase(Long long1, String string, String string2, 
	    		Object object, String string3, Object object2) {
	    }

	    public DataBase(Long id, String message, String createdBy, 
	    		LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt) {
	        this.id = id;
	        this.message = message;
	        this.createdAt = createdAt;
	        this.createdBy = createdBy;
	        this.updatedAt = updatedAt;
	        this.updatedBy = updatedBy;
	    }

	    public static DataBase fromLogString(String dataLog) {
	        List<String> data = Arrays.asList(dataLog.split(","));
	        return new DataBase(
	                Long.valueOf(data.get(0)),
	                Optional.ofNullable(data.get(1)).orElse(null),
	                Optional.ofNullable(data.get(2)).orElse(null),
	                Optional.ofNullable(data.get(3)).map(DataBase::resolveDate).orElse(null),
	                Optional.ofNullable(data.get(4)).orElse(null),
	                Optional.ofNullable(data.get(5)).map(DataBase::resolveDate).orElse(null));
	    }

	    private static LocalDateTime resolveDate(String date) {
	        if (date.equals("null")) {
	            return null;
	        }
	        return LocalDateTime.parse(date);
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }

	    public LocalDateTime getUpdatedAt() {
	        return updatedAt;
	    }

	    public void setUpdatedAt(LocalDateTime updatedAt) {
	        this.updatedAt = updatedAt;
	    }

	    public String getCreatedBy() {
	        return createdBy;
	    }

	    public void setCreatedBy(String createdBy) {
	        this.createdBy = createdBy;
	    }

	    public String getUpdatedBy() {
	        return updatedBy;
	    }

	    public void setUpdatedBy(String updatedBy) {
	        this.updatedBy = updatedBy;
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String toString() {
	        return "id: " + id + ", " +
	                "message: " + message + ", " +
	                "createdBy: " + createdBy + ", " +
	                "createdAt: " + createdAt + ", " +
	                "updatedBy: " + updatedBy + ", " +
	                "updatedAt: " + updatedAt;
	    }
	}