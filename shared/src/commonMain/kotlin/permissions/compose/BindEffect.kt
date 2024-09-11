/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package permissions.compose

import androidx.compose.runtime.Composable
import permissions.PermissionsController

@Suppress("FunctionNaming")
@Composable
expect fun BindEffect(permissionsController: PermissionsController)
