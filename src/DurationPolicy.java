public final class DurationPolicy implements WorkshopPolicy {
    @Override public boolean permits(Registration registration) {
        // TODO: implement the duration and induction rules in the practice brief.
        if (registration == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        
        if (registration.hours() >= 5 && registration.hours() <= 8) {
            return false;
        }
        else if ((registration.hours() >=3 && registration.hours() <= 4) && (registration.inductionCompleted() == true)) {
            return true;
        }
        else if (registration.hours() >=1 && registration.hours() <= 2) {
            return true;
                }
        else {
            return false;
        }
    }
}
