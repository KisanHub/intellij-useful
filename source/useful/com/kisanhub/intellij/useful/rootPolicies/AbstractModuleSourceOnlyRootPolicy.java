/*
 * This file is part of java2c. It is subject to the licence terms in the COPYRIGHT file found in the top-level directory of this distribution and at https://raw.githubusercontent.com/raphaelcohn/java2c/master/COPYRIGHT. No part of compilerUser, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYRIGHT file.
 * Copyright © 2014-2015 The developers of java2c. See the COPYRIGHT file in the top-level directory of this distribution and at https://raw.githubusercontent.com/raphaelcohn/java2c/master/COPYRIGHT.
 */

package com.kisanhub.intellij.useful.rootPolicies;

import com.intellij.openapi.roots.*;
import com.kisanhub.intellij.useful.projectValidationMessagesRecorders.ProjectValidationMessagesRecorder;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractModuleSourceOnlyRootPolicy extends NonNullPerOrderEntryRootPolicy<ProjectValidationMessagesRecorder>
{
	@NotNull
	@Override
	public final ProjectValidationMessagesRecorder visitLibraryOrderEntry(@NotNull final LibraryOrderEntry libraryOrderEntry, @SuppressWarnings("NullableProblems") @NotNull final ProjectValidationMessagesRecorder initialValue)
	{
		return initialValue;
	}

	@NotNull
	@Override
	public final ProjectValidationMessagesRecorder visitModuleOrderEntry(@NotNull final ModuleOrderEntry moduleOrderEntry, @SuppressWarnings("NullableProblems") @NotNull final ProjectValidationMessagesRecorder initialValue)
	{
		return initialValue;
	}

	@NotNull
	@Override
	public final ProjectValidationMessagesRecorder visitModuleJdkOrderEntry(@NotNull final ModuleJdkOrderEntry jdkOrderEntry, @SuppressWarnings("NullableProblems") @NotNull final ProjectValidationMessagesRecorder initialValue)
	{
		return initialValue;
	}

	@NotNull
	@Override
	public final ProjectValidationMessagesRecorder visitInheritedJdkOrderEntry(@NotNull final InheritedJdkOrderEntry inheritedJdkOrderEntry, @SuppressWarnings("NullableProblems") @NotNull final ProjectValidationMessagesRecorder initialValue)
	{
		return initialValue;
	}
}
