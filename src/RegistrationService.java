public final class RegistrationService {
    private final WorkshopPolicy policy;
    public RegistrationService(WorkshopPolicy policy) {
        if (policy == null) throw new IllegalArgumentException("A policy is required.");
        this.policy = policy;
    }
    public void confirm(Registration registration) {
        // TODO: validate the request, consult the injected policy and record the decision.
        if (registration == null) {
            throw new IllegalArgumentException("registration cannot be null");
        }
        if (registration.status() != RegistrationStatus.PENDING) {
            throw new IllegalStateException("Request already has a decision");
        registration.
        throw new UnsupportedOperationException("Complete RegistrationService.confirm");
    }
}
