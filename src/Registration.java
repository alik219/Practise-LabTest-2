public final class Registration {
    private final String id;
    private final int hours;
    private final boolean inductionCompleted;
    private RegistrationStatus status = RegistrationStatus.PENDING;
    public Registration(String id, int hours, boolean inductionCompleted) {
        if (id == null || id.isBlank() || hours < 1 || hours > 8)
            throw new IllegalArgumentException("Use a nonblank ID and hours from 1 to 8.");
        this.id = id; this.hours = hours; this.inductionCompleted = inductionCompleted;
    }
    public String id() { return id; }
    public int hours() { return hours; }
    public boolean inductionCompleted() { return inductionCompleted; }
    public RegistrationStatus status() { return status; }
    void recordDecision(boolean permitted) {
        if (status != RegistrationStatus.PENDING) throw new IllegalStateException("Already decided.");
        status = permitted ? RegistrationStatus.CONFIRMED : RegistrationStatus.DECLINED;
    }
}
