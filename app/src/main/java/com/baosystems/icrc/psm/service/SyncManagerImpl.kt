package com.baosystems.icrc.psm.service

import io.reactivex.Observable
import org.hisp.dhis.android.core.D2
import org.hisp.dhis.android.core.arch.call.D2Progress

class SyncManagerImpl(private val d2: D2): SyncManager {
    override fun metadataSync(): Observable<D2Progress> {
        return Observable.defer {
            d2.metadataModule().download()
        }
    }

    override fun dataSync(program: String): Observable<D2Progress> {
        return Observable.defer{
            d2.trackedEntityModule()
                .trackedEntityInstanceDownloader()
                .byProgramUid(program)
                .limitByOrgunit(true)
                .limitByProgram(true)
                .download()
        }
    }
}