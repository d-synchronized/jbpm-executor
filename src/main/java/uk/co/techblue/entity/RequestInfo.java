/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package uk.co.techblue.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.persistence.*;


@Entity(name = "RequestInfo")
@Vetoed
public class RequestInfo {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The time. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    /** The request status. */
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    /** The command name. */
    private String commandName;

    /** The message. */
    private String message;

    /** The key. */
   @Column(name="bussiness_key") 
    private String key;

    /** The retries. */
    private int retries = 0;

    /** The executions. */
    private int executions = 0;

    /** The request data. */
    @Lob
    private byte[] requestData;

    /** The response data. */
    @Lob
    private byte[] responseData;

    /** The error info. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestInfo")
    private List<ErrorInfo> errorInfo = new ArrayList<ErrorInfo>();

    /**
     * Instantiates a new request info.
     */
    public RequestInfo() {
    }

    /**
     * Gets the error info.
     * 
     * @return the error info
     */
    public List<ErrorInfo> getErrorInfo() {
        return errorInfo;
    }

    /**
     * Sets the error info.
     * 
     * @param errorInfo the new error info
     */
    public void setErrorInfo(List<ErrorInfo> errorInfo) {
        this.errorInfo = errorInfo;
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
     * Gets the retries.
     * 
     * @return the retries
     */
    public int getRetries() {
        return retries;
    }

    /**
     * Sets the retries.
     * 
     * @param retries the new retries
     */
    public void setRetries(int retries) {
        this.retries = retries;
    }

    /**
     * Gets the executions.
     * 
     * @return the executions
     */
    public int getExecutions() {
        return executions;
    }

    /**
     * Sets the executions.
     * 
     * @param executions the new executions
     */
    public void setExecutions(int executions) {
        this.executions = executions;
    }

    /**
     * Gets the command name.
     * 
     * @return the command name
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Sets the command name.
     * 
     * @param commandName the new command name
     */
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Gets the key.
     * 
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key.
     * 
     * @param key the new key
     */
    public void setKey(String key) {
        this.key = key;
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
     * Gets the request data.
     * 
     * @return the request data
     */
    public byte[] getRequestData() {
        return requestData;
    }

    /**
     * Sets the request data.
     * 
     * @param requestData the new request data
     */
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    /**
     * Gets the response data.
     * 
     * @return the response data
     */
    public byte[] getResponseData() {
        return responseData;
    }

    /**
     * Sets the response data.
     * 
     * @param responseData the new response data
     */
    public void setResponseData(byte[] responseData) {
        this.responseData = responseData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RequestInfo{" + "id=" + id + ", time=" + time + ", status=" + requestStatus + ", commandName=" + commandName
                + ", message=" + message + ", key=" + key + ", requestData=" + requestData + ", responseData=" + responseData
                + ", error=" + errorInfo + '}';
    }

    /**
     * Gets the request status.
     * 
     * @return the request status
     */
    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    /**
     * Sets the request status.
     * 
     * @param requestStatus the new request status
     */
    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    /*
     * (non-Javadoc)
     * 
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
        final RequestInfo other = (RequestInfo) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.time != other.time && (this.time == null || !this.time.equals(other.time))) {
            return false;
        }
        if (this.requestStatus != other.requestStatus) {
            return false;
        }
        if ((this.commandName == null) ? (other.commandName != null) : !this.commandName.equals(other.commandName)) {
            return false;
        }
        if ((this.message == null) ? (other.message != null) : !this.message.equals(other.message)) {
            return false;
        }
        if ((this.key == null) ? (other.key != null) : !this.key.equals(other.key)) {
            return false;
        }
        if (!Arrays.equals(this.requestData, other.requestData)) {
            return false;
        }
        if (!Arrays.equals(this.responseData, other.responseData)) {
            return false;
        }
        if (this.errorInfo != other.errorInfo && (this.errorInfo == null || !this.errorInfo.equals(other.errorInfo))) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 79 * hash + (this.time != null ? this.time.hashCode() : 0);
        hash = 79 * hash + (this.requestStatus != null ? this.requestStatus.hashCode() : 0);
        hash = 79 * hash + (this.commandName != null ? this.commandName.hashCode() : 0);
        hash = 79 * hash + (this.message != null ? this.message.hashCode() : 0);
        hash = 79 * hash + (this.key != null ? this.key.hashCode() : 0);
        hash = 79 * hash + Arrays.hashCode(this.requestData);
        hash = 79 * hash + Arrays.hashCode(this.responseData);
        hash = 79 * hash + (this.errorInfo != null ? this.errorInfo.hashCode() : 0);
        return hash;
    }

}
