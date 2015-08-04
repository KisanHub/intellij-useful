/*
 * This file is part of intellij-orogeny. It is subject to the licence terms in the COPYRIGHT file found in the top-level directory of this distribution and at https://raw.githubusercontent.com/KisanHub/intellij-orogeny/master/COPYRIGHT. No part of intellij-orogeny, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYRIGHT file.
 * Copyright © 2015 The developers of intellij-orogeny. See the COPYRIGHT file in the top-level directory of this distribution and at https://raw.githubusercontent.com/KisanHub/intellij-orogeny/master/COPYRIGHT.
 */

package com.kisanhub.intellij.useful.rootPolicies;

import com.intellij.openapi.roots.*;
import org.jetbrains.annotations.NotNull;

public final class DoNothingNonNullPerOrderEntryRootPolicy<R> extends NonNullPerOrderEntryRootPolicy<R>
{
	@NotNull
	private static final DoNothingNonNullPerOrderEntryRootPolicy<?> singleton = new DoNothingNonNullPerOrderEntryRootPolicy<Object>();

	@SuppressWarnings({"unchecked", "CastToConcreteClass", "MethodNamesDifferingOnlyByCase", "StaticMethodOnlyUsedInOneClass"})
	@NotNull
	public static <R> NonNullPerOrderEntryRootPolicy<R> doNothingPerOrderEntryRootPolicy()
	{
		return (NonNullPerOrderEntryRootPolicy<R>) singleton;
	}

	private DoNothingNonNullPerOrderEntryRootPolicy()
	{
	}

	@NotNull
	@Override
	public R visitModuleSourceOrderEntry(@NotNull final ModuleSourceOrderEntry moduleSourceOrderEntry, @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter") @NotNull final R initialValue)
	{
		return initialValue;
	}

	@NotNull
	@Override
	public R visitLibraryOrderEntry(@NotNull final LibraryOrderEntry libraryOrderEntry, @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter") @NotNull final R initialValue)
	{
		return initialValue;
	}

	@NotNull
	@Override
	public R visitModuleOrderEntry(@NotNull final ModuleOrderEntry moduleOrderEntry, @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter") @NotNull final R initialValue)
	{
		return initialValue;
	}

	@NotNull
	@Override
	public R visitModuleJdkOrderEntry(@NotNull final ModuleJdkOrderEntry jdkOrderEntry, @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter") @NotNull final R initialValue)
	{
		return initialValue;
	}

	@NotNull
	@Override
	public R visitInheritedJdkOrderEntry(@NotNull final InheritedJdkOrderEntry inheritedJdkOrderEntry, @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter") @NotNull final R initialValue)
	{
		return initialValue;
	}
}
