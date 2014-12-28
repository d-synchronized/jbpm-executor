package uk.co.techblue.entity;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The Class ErrorInfo.
 */
@Entity
@Vetoed
public class ErrorInfo implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1201670646652755481L;
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /** The time. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    
    /** The message. */
    private String message;
    
    /** The stacktrace. */
    @Column(length = 5000)
    private String stacktrace;

    /** The request info. */
    @ManyToOne
    @JoinColumn(name = "REQUEST_ID", nullable = false)
    private RequestInfo requestInfo;

    /**
     * Instantiates a new error info.
     */
    public ErrorInfo() {
    }

    /**
     * Instantiates a new error info.
     *
     * @param message the message
     * @param stacktrace the stacktrace
     */
    public ErrorInfo(String message, String stacktrace) {
        this.message = message;
        this.stacktrace = stacktrace;
        this.time = new Date();
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the stacktrace.
     *
     * @return the stacktrace
     */
    public String getStacktrace() {
        return stacktrace;
    }

    /**
     * Sets the stacktrace.
     *
     * @param stacktrace the new stacktrace
     */
    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    /**
     * Gets the time.
     *
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the time.
     *
     * @param time the new time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Gets the request info.
     *
     * @return the request info
     */
    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    /**
     * Sets the request info.
     *
     * @param requestInfo the new request info
     */
    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ErrorInfo{" + "id=" + id + ", time=" + time + ", message=" + message + ", stacktrace=" + stacktrace
                + ", requestInfo=" + requestInfo.getId() + '}';
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ErrorInfo other = (ErrorInfo) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.time != other.time && (this.time == null || !this.time.equals(other.time))) {
            return false;
        }
        if ((this.message == null) ? (other.message != null) : !this.message.equals(other.message)) {
            return false;
        }
        if ((this.stacktrace == null) ? (other.stacktrace != null) : !this.stacktrace.equals(other.stacktrace)) {
            return false;
        }
        if (this.requestInfo != other.requestInfo && (this.requestInfo == null || !this.requestInfo.equals(other.requestInfo))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.time != null ? this.time.hashCode() : 0);
        hash = 37 * hash + (this.message != null ? this.message.hashCode() : 0);
        hash = 37 * hash + (this.stacktrace != null ? this.stacktrace.hashCode() : 0);
        hash = 37 * hash + (this.requestInfo != null ? this.requestInfo.hashCode() : 0);
        return hash;
    }

}
