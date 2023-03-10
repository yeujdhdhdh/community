package life.study.community.model;

public class Notification extends NotificationKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.gmt_create
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    private Long gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.status
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.notifier
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    private Integer notifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.receiver
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    private Integer receiver;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.outerId
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    private Integer outerid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.type
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.notifierName
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    private String notifiername;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.outerTitle
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    private String outertitle;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.gmt_create
     *
     * @return the value of notification.gmt_create
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.gmt_create
     *
     * @param gmtCreate the value for notification.gmt_create
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.status
     *
     * @return the value of notification.status
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.status
     *
     * @param status the value for notification.status
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.notifier
     *
     * @return the value of notification.notifier
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public Integer getNotifier() {
        return notifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.notifier
     *
     * @param notifier the value for notification.notifier
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public void setNotifier(Integer notifier) {
        this.notifier = notifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.receiver
     *
     * @return the value of notification.receiver
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public Integer getReceiver() {
        return receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.receiver
     *
     * @param receiver the value for notification.receiver
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.outerId
     *
     * @return the value of notification.outerId
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public Integer getOuterid() {
        return outerid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.outerId
     *
     * @param outerid the value for notification.outerId
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public void setOuterid(Integer outerid) {
        this.outerid = outerid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.type
     *
     * @return the value of notification.type
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.type
     *
     * @param type the value for notification.type
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.notifierName
     *
     * @return the value of notification.notifierName
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public String getNotifiername() {
        return notifiername;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.notifierName
     *
     * @param notifiername the value for notification.notifierName
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public void setNotifiername(String notifiername) {
        this.notifiername = notifiername == null ? null : notifiername.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.outerTitle
     *
     * @return the value of notification.outerTitle
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public String getOutertitle() {
        return outertitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.outerTitle
     *
     * @param outertitle the value for notification.outerTitle
     *
     * @mbggenerated Sat Feb 25 21:21:36 CST 2023
     */
    public void setOutertitle(String outertitle) {
        this.outertitle = outertitle == null ? null : outertitle.trim();
    }
}