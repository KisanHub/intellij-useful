/*
 * This file is part of intellij-useful. It is subject to the licence terms in the COPYRIGHT file found in the top-level directory of this distribution and at https://raw.githubusercontent.com/KisanHub/intellij-useful/master/COPYRIGHT. No part of intellij-orogeny, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYRIGHT file.
 * Copyright © 2015 The developers of intellij-useful. See the COPYRIGHT file in the top-level directory of this distribution and at https://raw.githubusercontent.com/KisanHub/intellij-useful/master/COPYRIGHT.
 */

package com.kisanhub.intellij.useful.rootPolicies;

import com.intellij.openapi.roots.RootPolicy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ProcessModuleOrder
{
	<R> void useModuleOrderEntriesInModuleDependencyOrder(@NotNull RootPolicy<R> rootPolicy, @Nullable final R initialValue);
}
