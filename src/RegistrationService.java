public final class RegistrationService {
    private final WorkshopPolicy policy;
    public RegistrationService(WorkshopPolicy policy) {
        if (policy == null) throw new IllegalArgumentException("A policy is required.");
        this.policy = policy;
    }
    public void confirm(Registration registration) {
        // TODO: validate the request, consult the injected policy and record the decision.
        throw new UnsupportedOperationException("Complete RegistrationService.confirm");
    }
}
