package net.cromulence.imgur.apiv3.datamodel;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PojosTest {

    // Configured for expectation, so we know when a class gets added or removed.
    private static final int EXPECTED_CLASS_COUNT = 36;

    // The packages to test
    private static final String DATAMODEL_PACKAGE = "net.cromulence.imgur.apiv3.datamodel";

    private static final String CONSTANTS_PACKAGE = DATAMODEL_PACKAGE + ".constants";
    private static final String MEME_PACKAGE      = DATAMODEL_PACKAGE + ".meme";
    private static final String SUBREDDIT_PACKAGE = DATAMODEL_PACKAGE + ".subreddit";

    private static final int EXPECTED_DATAMODEL_POJO_COUNT = 36;
    private static final int EXPECTED_CONSTANTS_POJO_COUNT = 14;
    private static final int EXPECTED_MEME_POJO_COUNT      = 6;
    private static final int EXPECTED_SUBREDDIT_POJO_COUNT = 5;

    @Test
    public void ensureExpectedPojoCount() {

        Affirm.affirmEquals(".datamodel classes added / removed?", EXPECTED_DATAMODEL_POJO_COUNT, getPojoClasses(DATAMODEL_PACKAGE).size());
        Affirm.affirmEquals(".constants classes added / removed?", EXPECTED_CONSTANTS_POJO_COUNT, getPojoClasses(CONSTANTS_PACKAGE).size());
        Affirm.affirmEquals(".meme classes added / removed?",      EXPECTED_MEME_POJO_COUNT, getPojoClasses(MEME_PACKAGE).size());
        Affirm.affirmEquals(".subreddit classes added / removed?", EXPECTED_SUBREDDIT_POJO_COUNT, getPojoClasses(SUBREDDIT_PACKAGE).size());
    }

    private List<PojoClass> getAllClasses() {
        ArrayList<PojoClass> pojoClasses = new ArrayList<>();

        pojoClasses.addAll(getPojoClasses(DATAMODEL_PACKAGE));
        pojoClasses.addAll(getPojoClasses(CONSTANTS_PACKAGE));
        pojoClasses.addAll(getPojoClasses(MEME_PACKAGE));
        pojoClasses.addAll(getPojoClasses(SUBREDDIT_PACKAGE));

        return pojoClasses;
    }

    private List<PojoClass> getPojoClasses(String packageName) {
        return PojoClassFactory.getPojoClasses(packageName, new FilterPackageInfo());
    }

    @Test
    public void testPojoStructureAndBehavior() {
        Validator validator = ValidatorBuilder.create()
            // Add Rules to validate structure for POJO_PACKAGE
            // See com.openpojo.validation.rule.impl for more ...
            .with(new GetterMustExistRule())
            .with(new SetterMustExistRule())
            // Add Testers to validate behaviour for POJO_PACKAGE
            // See com.openpojo.validation.test.impl for more ...
            .with(new SetterTester())
            .with(new GetterTester())
            .build();

        validator.validate(getAllClasses());
    }

}
