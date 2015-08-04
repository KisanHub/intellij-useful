/*
 * This file is part of java2c. It is subject to the licence terms in the COPYRIGHT file found in the top-level directory of this distribution and at https://raw.githubusercontent.com/raphaelcohn/java2c/master/COPYRIGHT. No part of compilerUser, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYRIGHT file.
 * Copyright © 2014-2015 The developers of java2c. See the COPYRIGHT file in the top-level directory of this distribution and at https://raw.githubusercontent.com/raphaelcohn/java2c/master/COPYRIGHT.
 */

package com.kisanhub.intellij.useful.rootPolicies;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.vfs.VirtualFile;
import com.kisanhub.intellij.useful.projectValidationMessagesRecorders.ProjectValidationMessagesRecorder;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.compiler.CompilerMessageCategory.ERROR;
import static com.intellij.openapi.roots.OrderRootType.getAllTypes;
import static com.kisanhub.intellij.useful.rootPolicies.DoNothingNonNullPerOrderEntryRootPolicy.doNothingPerOrderEntryRootPolicy;
import static java.lang.String.format;
import static java.util.Locale.ENGLISH;

public final class OrderEntryValidatingRootPolicy extends NonNullPerOrderEntryRootPolicy<ProjectValidationMessagesRecorder>
{
	@NonNls
	@NotNull
	private static final String OrderEntrySubCategory = "OrderEntry";

	private static final int OrderEntryLength = OrderEntry.class.getSimpleName().length();

	@NotNull
	private static final NonNullPerOrderEntryRootPolicy<ProjectValidationMessagesRecorder> convenientConstruction = doNothingPerOrderEntryRootPolicy();

	@NotNull
	public static final RootPolicy<ProjectValidationMessagesRecorder> OrderEntryValidatingRootPolicyInstance = new OrderEntryValidatingRootPolicy(convenientConstruction);

	@NotNull
	private final NonNullPerOrderEntryRootPolicy<ProjectValidationMessagesRecorder> visitorOnlyIfOrderEntryRootsAndFilesAreValid;

	public OrderEntryValidatingRootPolicy(@NotNull final NonNullPerOrderEntryRootPolicy<ProjectValidationMessagesRecorder> visitorOnlyIfOrderEntryRootsAndFilesAreValid)
	{
		this.visitorOnlyIfOrderEntryRootsAndFilesAreValid = visitorOnlyIfOrderEntryRootsAndFilesAreValid;
	}

	@NotNull
	@Override
	public ProjectValidationMessagesRecorder visitModuleSourceOrderEntry(@NotNull final ModuleSourceOrderEntry moduleSourceOrderEntry, @SuppressWarnings("NullableProblems") @NotNull final ProjectValidationMessagesRecorder initialValue)
	{
		// Once
		if (validateOrderEntry(initialValue, moduleSourceOrderEntry, ModuleSourceOrderEntry.class))
		{
			visitorOnlyIfOrderEntryRootsAndFilesAreValid.visitModuleSourceOrderEntry(moduleSourceOrderEntry, initialValue);
		}
		return initialValue;
	}

	@NotNull
	@Override
	public ProjectValidationMessagesRecorder visitLibraryOrderEntry(@NotNull final LibraryOrderEntry libraryOrderEntry, @SuppressWarnings("NullableProblems") @NotNull final ProjectValidationMessagesRecorder initialValue)
	{
		// Multiple
		if (validateOrderEntry(initialValue, libraryOrderEntry, LibraryOrderEntry.class))
		{
			visitorOnlyIfOrderEntryRootsAndFilesAreValid.visitLibraryOrderEntry(libraryOrderEntry, initialValue);
		}
		return initialValue;
	}

	@NotNull
	@Override
	public ProjectValidationMessagesRecorder visitModuleOrderEntry(@NotNull final ModuleOrderEntry moduleOrderEntry, @SuppressWarnings("NullableProblems") @NotNull final ProjectValidationMessagesRecorder initialValue)
	{
		// Multiple
		if (validateOrderEntry(initialValue, moduleOrderEntry, ModuleOrderEntry.class))
		{
			visitorOnlyIfOrderEntryRootsAndFilesAreValid.visitModuleOrderEntry(moduleOrderEntry, initialValue);
		}
		return initialValue;
	}

	@NotNull
	@Override
	public ProjectValidationMessagesRecorder visitModuleJdkOrderEntry(@NotNull final ModuleJdkOrderEntry jdkOrderEntry, @SuppressWarnings("NullableProblems") @NotNull final ProjectValidationMessagesRecorder initialValue)
	{
		// Once or Never
		if (validateOrderEntry(initialValue, jdkOrderEntry, ModuleJdkOrderEntry.class))
		{
			visitorOnlyIfOrderEntryRootsAndFilesAreValid.visitModuleJdkOrderEntry(jdkOrderEntry, initialValue);
		}
		return initialValue;
	}

	@NotNull
	@Override
	public ProjectValidationMessagesRecorder visitInheritedJdkOrderEntry(@NotNull final InheritedJdkOrderEntry inheritedJdkOrderEntry, @SuppressWarnings("NullableProblems") @NotNull final ProjectValidationMessagesRecorder initialValue)
	{
		// Once or Never
		if (validateOrderEntry(initialValue, inheritedJdkOrderEntry, InheritedJdkOrderEntry.class))
		{
			visitorOnlyIfOrderEntryRootsAndFilesAreValid.visitInheritedJdkOrderEntry(inheritedJdkOrderEntry, initialValue);
		}
		return initialValue;
	}

	private static <O extends OrderEntry> boolean validateOrderEntry(@NotNull final ProjectValidationMessagesRecorder projectValidationMessagesRecorder, @NotNull final O orderEntry, @NotNull final Class<O> orderEntryClass)
	{
		if (!orderEntry.isValid())
		{
			assert ERROR != null;
			projectValidationMessagesRecorder.record(ERROR, OrderEntrySubCategory, getOrderEntryDescription(orderEntry, orderEntryClass));
			return false;
		}

		final OrderRootType[] allTypes = getAllTypes();
		assert allTypes != null;

		boolean areAllRootsSoFarValid = true;
		for (final OrderRootType orderRootType : allTypes)
		{
			final boolean isRootInvalid = !validateRoots(projectValidationMessagesRecorder, orderEntry, orderEntryClass, orderRootType);
			if (isRootInvalid)
			{
				areAllRootsSoFarValid = false;
			}
		}

		return areAllRootsSoFarValid;
	}

	private static <O extends OrderEntry> boolean validateRoots(@NotNull final ProjectValidationMessagesRecorder projectValidationMessagesRecorder, @NotNull final O orderEntry, @NotNull final Class<O> orderEntryClass, @NotNull final OrderRootType orderRootType)
	{
		boolean areAllFileSoFarValid = true;
		for (final VirtualFile file : orderEntry.getFiles(orderRootType))
		{
			if (!file.isValid())
			{
				assert ERROR != null;
				final String format = format(ENGLISH, "Root '%1$s' of type '%2$s' in %3$s", file.getName(), orderRootType.name(), getOrderEntryDescription(orderEntry, orderEntryClass)); //NON-NLS
				assert format != null;
				projectValidationMessagesRecorder.record(ERROR, OrderEntrySubCategory, format);
				areAllFileSoFarValid = false;
			}
		}
		return areAllFileSoFarValid;
	}

	@NotNull
	private static <O extends OrderEntry> String getOrderEntryDescription(@NotNull final O orderEntry, @NotNull final Class<O> orderEntryClass)
	{
		final Module ownerModule = orderEntry.getOwnerModule();
		final String simpleName = orderEntryClass.getSimpleName();
		final String format = format(ENGLISH, "%1$s '%2$s' in module '%3$s' is invalid", simpleName.substring(0, simpleName.length() - OrderEntryLength), orderEntry.getPresentableName(), ownerModule.getName()); //NON-NLS
		assert format != null;
		return format;
	}

}
