package jetbrains.teamcity.server;

import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.teamcity.common.EchoRunnerConstants;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static jetbrains.teamcity.common.EchoRunnerConstants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EchoRunner extends RunType {

  private final PluginDescriptor descriptor;

  public EchoRunner(RunTypeRegistry registry, PluginDescriptor descriptor) {
    this.descriptor = descriptor;
    registry.registerRunType(this);
  }

  @NotNull
  @Override
  public String getType() {
    return EchoRunnerConstants.RUNNER_TYPE;
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return "Sonatype Scan";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "This is Sonatype Scanner";
  }

  @Nullable
  @Override
  public PropertiesProcessor getRunnerPropertiesProcessor() {
    return properties -> {
      final List<InvalidProperty> invalidProperties = new ArrayList<>();

      final String message = properties.get(MESSAGE_KEY);
      if (message == null) {
        invalidProperties.add(new InvalidProperty(MESSAGE_KEY, "Should not be null"));
      } else if (message.startsWith("fail")) {
        invalidProperties.add(new InvalidProperty(MESSAGE_KEY, "Should not start with fail"));
      }

      return invalidProperties;
    };
  }

  @Nullable
  @Override
  public String getEditRunnerParamsJspFilePath() {
    return descriptor.getPluginResourcesPath("editEchoRunnerParameters.jsp");
  }

  @Nullable
  @Override
  public String getViewRunnerParamsJspFilePath() {
    return descriptor.getPluginResourcesPath("viewEchoRunnerParameters.jsp");
  }

  @Nullable
  @Override
  public Map<String, String> getDefaultRunnerProperties() {
    return new HashMap<>();
  }


  @NotNull
  @Override
  public String describeParameters(@NotNull Map<String, String> parameters) {
    return "IQServer: '" + parameters.get(IQ_SERVER_KEY) + "'; Stage:'" + parameters.get(IQ_STAGE_KEY) + "'";
  }
}