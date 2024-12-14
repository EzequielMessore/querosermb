package br.com.messore.tech.exchages

import android.app.Application
import br.com.messore.tech.exchages.di.AppModule
import br.com.messore.tech.exchanges.core.data.di.DataModule
import br.com.messore.tech.exchanges.core.domain.di.DomainModule
import br.com.messore.tech.exchanges.core.local.di.LocalModule
import br.com.messore.tech.exchanges.core.remote.di.NetworkModule
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module
import timber.log.Timber

class ExchangesApp : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@ExchangesApp)
            defaultModule()
            modules(
                defaultModule,
                AppModule().module,
                DataModule().module,
                DomainModule().module,
                LocalModule().module,
                NetworkModule().module,
            )
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.10)
                    .build()
            }
            .build()
    }
}
