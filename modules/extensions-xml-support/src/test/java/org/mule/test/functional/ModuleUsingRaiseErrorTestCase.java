/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.functional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.functional.junit4.matchers.MessageMatchers.hasPayload;
import static org.mule.test.allure.AllureConstants.ErrorHandlingFeature.ERROR_HANDLING;
import static org.mule.test.allure.AllureConstants.ErrorHandlingFeature.ErrorHandlingStory.RAISE_ERROR;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.Ignore;
import org.junit.Test;

@Feature(ERROR_HANDLING)
@Story(RAISE_ERROR)
public class ModuleUsingRaiseErrorTestCase extends AbstractXmlExtensionMuleArtifactFunctionalTestCase {

  @Override
  protected String getModulePath() {
    return "modules/module-using-raise-error.xml";
  }

  @Override
  protected String getConfigFile() {
    return "flows/flows-with-module-using-raise-error.xml";
  }

  @Test
  public void muleStaticErrorRaised() throws Exception {
    verifyResultFrom("simple", "Could not connect: A module error occurred.");
  }

  @Test
  public void customStaticErrorRaised() throws Exception {
    verifyResultFrom("complex", "Custom error: A custom error occurred.");
  }

  @Test
  public void muleParameterErrorRaised() throws Exception {
    verifyResultFrom("simpleProxy", "Could not route: A bad error occurred.");
  }

  @Test
  public void customParameterErrorRaised() throws Exception {
    verifyResultFrom("complexProxy", "Custom error: Something went wrong.");
  }

  @Test
  public void muleErrorCanBeMapped() throws Exception {
    verifyResultFrom("simpleMapping", "Handled");
  }

  @Test
  @Ignore("MULE-13609: When more than one processor is present, error mappings in smart connectors don't work")
  public void customErrorCanBeMapped() throws Exception {
    verifyResultFrom("complexMapping", "Handled");
  }

  private void verifyResultFrom(String flowName, String expectedPayload) throws Exception {
    assertThat(flowRunner(flowName).run().getMessage(), hasPayload(equalTo(expectedPayload)));
  }

}
