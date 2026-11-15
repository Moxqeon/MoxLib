package org.moxqeon.platform

import org.moxqeon.api.Accessor

/**
 * 平台插件访问器接口。
 *
 * 定义对指定平台插件实例 [P] 的访问能力。
 */
interface PlatformPluginAccessor<P : PlatformPlugin> : Accessor<P> {
}