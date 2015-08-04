/*
 * This file is part of intellij-orogeny. It is subject to the licence terms in the COPYRIGHT file found in the top-level directory of this distribution and at https://raw.githubusercontent.com/KisanHub/intellij-orogeny/master/COPYRIGHT. No part of intellij-orogeny, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYRIGHT file.
 * Copyright © 2015 The developers of intellij-orogeny. See the COPYRIGHT file in the top-level directory of this distribution and at https://raw.githubusercontent.com/KisanHub/intellij-orogeny/master/COPYRIGHT.
 */

package com.kisanhub.intellij.useful.commandLine.external;

import joptsimple.ArgumentAcceptingOptionSpec;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.kisanhub.intellij.useful.commandLine.external.UsefulOptionParser.printUnexpectedFailureAndExitAbnormally;
import static com.kisanhub.intellij.useful.commandLine.external.support.PathManagerHacker.DefaultConfigFolderName;
import static com.kisanhub.intellij.useful.commandLine.external.support.PathManagerHacker.MacOsXHomePath;

public class UsefulEntryPoint
{
	@NonNls
	@NotNull
	private static final String PATH = "PATH";

	@NotNull
	private final String abstractCommandLineApplicationStarterExClassName;

	@NotNull
	private final UsefulOptionParser usefulOptionParser;

	@SuppressWarnings("ConstantNamingConvention")
	@NotNull
	private final ArgumentAcceptingOptionSpec<File> home;

	@SuppressWarnings("ConstantNamingConvention")
	@NotNull
	private final ArgumentAcceptingOptionSpec<String> configFolder;

	@SuppressWarnings("ConstantNamingConvention")
	@NotNull
	private final ArgumentAcceptingOptionSpec<File> project;

	public UsefulEntryPoint(@NotNull final String abstractCommandLineApplicationStarterExClassName)
	{
		this.abstractCommandLineApplicationStarterExClassName = abstractCommandLineApplicationStarterExClassName;
		usefulOptionParser = new UsefulOptionParser();
		home = usefulOptionParser.accepts("home", "IntelliJ home path").withRequiredArg().describedAs(PATH).ofType(File.class).defaultsTo(new File(MacOsXHomePath));
		configFolder = usefulOptionParser.accepts("config-folder", "IntelliJ config folder name").withRequiredArg().describedAs("NAME").ofType(String.class).defaultsTo(DefaultConfigFolderName);
		project = usefulOptionParser.accepts("project", "IntelliJ project to build path").withRequiredArg().describedAs(PATH).ofType(File.class);
	}

	@SuppressWarnings("unused")
	@NotNull
	public UsefulEntryPoint alsoAccepts(@NonNls @NotNull final String option, @NonNls @NotNull final String description)
	{
		usefulOptionParser.accepts(option, description);
		return this;
	}

	public final void execute(@NotNull final String... commandLineArguments)
	{
		@NotNull final UsefulOptionSet usefulOptionSet = usefulOptionParser.parse(commandLineArguments);

		final File homePath = usefulOptionSet.getExtantDirectoryFileOptionValue(home);

		final String configFolderName = usefulOptionSet.getOptionValue(configFolder);

		final File projectPath = usefulOptionSet.getExtantFileOptionValue(project);

		final List<String> arguments = new ArrayList<String>(1);
		arguments.add(projectPath.getPath());
		argumentsAlsoAccepted(arguments);

		final String[] argumentsToPass = arguments.toArray(new String[arguments.size()]);

		// Has to be done this way using reflection (and a string at that, not a class instance) to prevent the class loading on a different ClassLoader before IntelliJ initialised
		try
		{
			new ExternalDriver(homePath, configFolderName).invoke(abstractCommandLineApplicationStarterExClassName, argumentsToPass);
		}
		catch (final Throwable t)
		{
			printUnexpectedFailureAndExitAbnormally(t);
		}
	}

	protected void argumentsAlsoAccepted(@SuppressWarnings("UnusedParameters") @NotNull final List<String> arguments)
	{
	}
}
