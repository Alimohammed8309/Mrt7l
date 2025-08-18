package com.mrt7l.ui.fragment.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class RequestPayModel {

    @JsonProperty("mrt7al")
    public Mrt7al getMrt7al() {
        return this.mrt7al; }
    public void setMrt7al(Mrt7al mrt7al) {
        this.mrt7al = mrt7al; }
    Mrt7al mrt7al;

    public class Activity{
        @JsonProperty("id")
        public String getId() {
            return this.id; }
        public void setId(String id) {
            this.id = id; }
        String id;
        @JsonProperty("object")
        public String getObject() {
            return this.object; }
        public void setObject(String object) {
            this.object = object; }
        String object;
        @JsonProperty("created")
        public long getCreated() {
            return this.created; }
        public void setCreated(long created) {
            this.created = created; }
        long created;
        @JsonProperty("status")
        public String getStatus() {
            return this.status; }
        public void setStatus(String status) {
            this.status = status; }
        String status;
        @JsonProperty("currency")
        public String getCurrency() {
            return this.currency; }
        public void setCurrency(String currency) {
            this.currency = currency; }
        String currency;
        @JsonProperty("amount")
        public int getAmount() {
            return this.amount; }
        public void setAmount(int amount) {
            this.amount = amount; }
        int amount;
        @JsonProperty("remarks")
        public String getRemarks() {
            return this.remarks; }
        public void setRemarks(String remarks) {
            this.remarks = remarks; }
        String remarks;
        @JsonProperty("txn_id")
        public String getTxn_id() {
            return this.txn_id; }
        public void setTxn_id(String txn_id) {
            this.txn_id = txn_id; }
        String txn_id;
    }

    public class Customer{
        @JsonProperty("first_name")
        public String getFirst_name() {
            return this.first_name; }
        public void setFirst_name(String first_name) {
            this.first_name = first_name; }
        String first_name;
        @JsonProperty("phone")
        public Phone getPhone() {
            return this.phone; }
        public void setPhone(Phone phone) {
            this.phone = phone; }
        Phone phone;
    }

    public class Data{
        @JsonProperty("id")
        public String getId() {
            return this.id; }
        public void setId(String id) {
            this.id = id; }
        String id;
        @JsonProperty("object")
        public String getObject() {
            return this.object; }
        public void setObject(String object) {
            this.object = object; }
        String object;
        @JsonProperty("live_mode")
        public boolean getLive_mode() {
            return this.live_mode; }
        public void setLive_mode(boolean live_mode) {
            this.live_mode = live_mode; }
        boolean live_mode;
        @JsonProperty("customer_initiated")
        public boolean getCustomer_initiated() {
            return this.customer_initiated; }
        public void setCustomer_initiated(boolean customer_initiated) {
            this.customer_initiated = customer_initiated; }
        boolean customer_initiated;
        @JsonProperty("api_version")
        public String getApi_version() {
            return this.api_version; }
        public void setApi_version(String api_version) {
            this.api_version = api_version; }
        String api_version;
        @JsonProperty("method")
        public String getMethod() {
            return this.method; }
        public void setMethod(String method) {
            this.method = method; }
        String method;
        @JsonProperty("status")
        public String getStatus() {
            return this.status; }
        public void setStatus(String status) {
            this.status = status; }
        String status;
        @JsonProperty("amount")
        public int getAmount() {
            return this.amount; }
        public void setAmount(int amount) {
            this.amount = amount; }
        int amount;
        @JsonProperty("currency")
        public String getCurrency() {
            return this.currency; }
        public void setCurrency(String currency) {
            this.currency = currency; }
        String currency;
        @JsonProperty("threeDSecure")
        public boolean getThreeDSecure() {
            return this.threeDSecure; }
        public void setThreeDSecure(boolean threeDSecure) {
            this.threeDSecure = threeDSecure; }
        boolean threeDSecure;
        @JsonProperty("card_threeDSecure")
        public boolean getCard_threeDSecure() {
            return this.card_threeDSecure; }
        public void setCard_threeDSecure(boolean card_threeDSecure) {
            this.card_threeDSecure = card_threeDSecure; }
        boolean card_threeDSecure;
        @JsonProperty("save_card")
        public boolean getSave_card() {
            return this.save_card; }
        public void setSave_card(boolean save_card) {
            this.save_card = save_card; }
        boolean save_card;
        @JsonProperty("product")
        public String getProduct() {
            return this.product; }
        public void setProduct(String product) {
            this.product = product; }
        String product;
        @JsonProperty("description")
        public String getDescription() {
            return this.description; }
        public void setDescription(String description) {
            this.description = description; }
        String description;
        @JsonProperty("metadata")
        public Metadata getMetadata() {
            return this.metadata; }
        public void setMetadata(Metadata metadata) {
            this.metadata = metadata; }
        Metadata metadata;
        @JsonProperty("order")
        public ArrayList<Object> getOrder() {
            return this.order; }
        public void setOrder(ArrayList<Object> order) {
            this.order = order; }
        ArrayList<Object> order;
        @JsonProperty("transaction")
        public Transaction getTransaction() {
            return this.transaction; }
        public void setTransaction(Transaction transaction) {
            this.transaction = transaction; }
        Transaction transaction;
        @JsonProperty("response")
        public Response getResponse() {
            return this.response; }
        public void setResponse(Response response) {
            this.response = response; }
        Response response;
        @JsonProperty("receipt")
        public Receipt getReceipt() {
            return this.receipt; }
        public void setReceipt(Receipt receipt) {
            this.receipt = receipt; }
        Receipt receipt;
        @JsonProperty("customer")
        public Customer getCustomer() {
            return this.customer; }
        public void setCustomer(Customer customer) {
            this.customer = customer; }
        Customer customer;
        @JsonProperty("merchant")
        public Merchant getMerchant() {
            return this.merchant; }
        public void setMerchant(Merchant merchant) {
            this.merchant = merchant; }
        Merchant merchant;
        @JsonProperty("source")
        public Source getSource() {
            return this.source; }
        public void setSource(Source source) {
            this.source = source; }
        Source source;
        @JsonProperty("redirect")
        public Redirect getRedirect() {
            return this.redirect; }
        public void setRedirect(Redirect redirect) {
            this.redirect = redirect; }
        Redirect redirect;
        @JsonProperty("post")
        public Post getPost() {
            return this.post; }
        public void setPost(Post post) {
            this.post = post; }
        Post post;
        @JsonProperty("activities")
        public ArrayList<Activity> getActivities() {
            return this.activities; }
        public void setActivities(ArrayList<Activity> activities) {
            this.activities = activities; }
        ArrayList<Activity> activities;
        @JsonProperty("auto_reversed")
        public boolean getAuto_reversed() {
            return this.auto_reversed; }
        public void setAuto_reversed(boolean auto_reversed) {
            this.auto_reversed = auto_reversed; }
        boolean auto_reversed;
    }

    public class Date{
        @JsonProperty("created")
        public long getCreated() {
            return this.created; }
        public void setCreated(long created) {
            this.created = created; }
        long created;
        @JsonProperty("transaction")
        public long getTransaction() {
            return this.transaction; }
        public void setTransaction(long transaction) {
            this.transaction = transaction; }
        long transaction;
    }

    public class Expiry{
        @JsonProperty("period")
        public int getPeriod() {
            return this.period; }
        public void setPeriod(int period) {
            this.period = period; }
        int period;
        @JsonProperty("type")
        public String getType() {
            return this.type; }
        public void setType(String type) {
            this.type = type; }
        String type;
    }

    public class Merchant{
        @JsonProperty("country")
        public String getCountry() {
            return this.country; }
        public void setCountry(String country) {
            this.country = country; }
        String country;
        @JsonProperty("currency")
        public String getCurrency() {
            return this.currency; }
        public void setCurrency(String currency) {
            this.currency = currency; }
        String currency;
        @JsonProperty("id")
        public String getId() {
            return this.id; }
        public void setId(String id) {
            this.id = id; }
        String id;
    }

    public class Metadata{
        @JsonProperty("reservation_id")
        public String getReservation_id() {
            return this.reservation_id; }
        public void setReservation_id(String reservation_id) {
            this.reservation_id = reservation_id; }
        String reservation_id;
    }

    public class Mrt7al{
        @JsonProperty("success")
        public boolean getSuccess() {
            return this.success; }
        public void setSuccess(boolean success) {
            this.success = success; }
        boolean success;
        @JsonProperty("msg")
        public String getMsg() {
            return this.msg; }
        public void setMsg(String msg) {
            this.msg = msg; }
        String msg;
        @JsonProperty("data")
        public Data getData() {
            return this.data; }
        public void setData(Data data) {
            this.data = data; }
        Data data;
    }

    public class Phone{
        @JsonProperty("country_code")
        public String getCountry_code() {
            return this.country_code; }
        public void setCountry_code(String country_code) {
            this.country_code = country_code; }
        String country_code;
        @JsonProperty("number")
        public String getNumber() {
            return this.number; }
        public void setNumber(String number) {
            this.number = number; }
        String number;
    }

    public class Post{
        @JsonProperty("status")
        public String getStatus() {
            return this.status; }
        public void setStatus(String status) {
            this.status = status; }
        String status;
        @JsonProperty("url")
        public String getUrl() {
            return this.url; }
        public void setUrl(String url) {
            this.url = url; }
        String url;
    }

    public class Receipt{
        @JsonProperty("email")
        public boolean getEmail() {
            return this.email; }
        public void setEmail(boolean email) {
            this.email = email; }
        boolean email;
        @JsonProperty("sms")
        public boolean getSms() {
            return this.sms; }
        public void setSms(boolean sms) {
            this.sms = sms; }
        boolean sms;
    }

    public class Redirect{
        @JsonProperty("status")
        public String getStatus() {
            return this.status; }
        public void setStatus(String status) {
            this.status = status; }
        String status;
        @JsonProperty("url")
        public String getUrl() {
            return this.url; }
        public void setUrl(String url) {
            this.url = url; }
        String url;
    }

    public class Response{
        @JsonProperty("code")
        public String getCode() {
            return this.code; }
        public void setCode(String code) {
            this.code = code; }
        String code;
        @JsonProperty("message")
        public String getMessage() {
            return this.message; }
        public void setMessage(String message) {
            this.message = message; }
        String message;
    }


    public class Source{
        @JsonProperty("object")
        public String getObject() {
            return this.object; }
        public void setObject(String object) {
            this.object = object; }
        String object;
        @JsonProperty("type")
        public String getType() {
            return this.type; }
        public void setType(String type) {
            this.type = type; }
        String type;
        @JsonProperty("payment_type")
        public String getPayment_type() {
            return this.payment_type; }
        public void setPayment_type(String payment_type) {
            this.payment_type = payment_type; }
        String payment_type;
        @JsonProperty("channel")
        public String getChannel() {
            return this.channel; }
        public void setChannel(String channel) {
            this.channel = channel; }
        String channel;
        @JsonProperty("id")
        public String getId() {
            return this.id; }
        public void setId(String id) {
            this.id = id; }
        String id;
        @JsonProperty("on_file")
        public boolean getOn_file() {
            return this.on_file; }
        public void setOn_file(boolean on_file) {
            this.on_file = on_file; }
        boolean on_file;
        @JsonProperty("payment_method")
        public String getPayment_method() {
            return this.payment_method; }
        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method; }
        String payment_method;
    }

    public class Transaction{
        @JsonProperty("timezone")
        public String getTimezone() {
            return this.timezone; }
        public void setTimezone(String timezone) {
            this.timezone = timezone; }
        String timezone;
        @JsonProperty("created")
        public String getCreated() {
            return this.created; }
        public void setCreated(String created) {
            this.created = created; }
        String created;
        @JsonProperty("url")
        public String getUrl() {
            return this.url; }
        public void setUrl(String url) {
            this.url = url; }
        String url;
        @JsonProperty("expiry")
        public Expiry getExpiry() {
            return this.expiry; }
        public void setExpiry(Expiry expiry) {
            this.expiry = expiry; }
        Expiry expiry;
        @JsonProperty("asynchronous")
        public boolean getAsynchronous() {
            return this.asynchronous; }
        public void setAsynchronous(boolean asynchronous) {
            this.asynchronous = asynchronous; }
        boolean asynchronous;
        @JsonProperty("amount")
        public int getAmount() {
            return this.amount; }
        public void setAmount(int amount) {
            this.amount = amount; }
        int amount;
        @JsonProperty("currency")
        public String getCurrency() {
            return this.currency; }
        public void setCurrency(String currency) {
            this.currency = currency; }
        String currency;
        @JsonProperty("date")
        public Date getDate() {
            return this.date; }
        public void setDate(Date date) {
            this.date = date; }
        Date date;
    }


}
