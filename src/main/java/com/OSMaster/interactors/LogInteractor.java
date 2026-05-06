package com.OSMaster.interactors;

import com.OSMaster.entities.LogEntity;

public interface LogInteractor {
    LogEntity iniciarLog(Object payload);
    void finalizarLog(LogEntity log, int statusCode, Object response);
}
