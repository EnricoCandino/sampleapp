package it.enricocandino.sample.service.robospice.request.user;

import java.text.SimpleDateFormat;
import java.util.Locale;

import it.enricocandino.sample.model.User;
import it.enricocandino.sample.service.model.Profile;
import it.enricocandino.sample.service.robospice.AbstractSpiceRequest;
import it.enricocandino.sample.service.robospice.BaseSpiceRequestListener;
import it.enricocandino.sample.service.robospice.service.UserService;
import it.enricocandino.sample.service.robospice.response.ProfileResponse;
import it.enricocandino.sample.util.Log;

/**
 * Created by enrico on 20/02/15.
 */
public class SpiceProfileRequest extends AbstractSpiceRequest<User, ProfileResponse, UserService> {

    private String LOGTAG = "GetProfileRequest";

    private User user;

    public SpiceProfileRequest(User user) {
        super(ProfileResponse.class, UserService.class);
        this.user = user;

        this.listener = new MyListener();
    }

    @Override
    public ProfileResponse loadDataFromNetwork() throws Exception {
        return getService().getProfile();
    }

    @Override
    public BaseSpiceRequestListener<User, ProfileResponse> getListener() {
        return listener;
    }

    private class MyListener extends BaseSpiceRequestListener<User, ProfileResponse> {

        @Override
        public User prepareResponse(ProfileResponse serverResponse) {
            Profile profile = serverResponse.getObject();

            if (user != null) {
                user.setName(profile.getFirstname());
                user.setSurname(profile.getLastname());
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    user.setBirthday(sdf.parse(profile.getBirthday()));
                } catch (Exception e) {
                    Log.e(LOGTAG, "Error while parsing date [" + profile.getBirthday() + "]", e);
                }
                user.setEmail(profile.getEmail());
                user.setUserId(Long.valueOf(profile.getId()));
                user.setJob(profile.getJob());
                if (profile.getSex() != null)
                    user.setSex(User.SEX.valueOf(profile.getSex().toUpperCase()));
            }

            return user;
        }
    }
}
