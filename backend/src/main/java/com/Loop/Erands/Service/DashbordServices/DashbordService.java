package com.Loop.Erands.Service.DashbordServices;

import com.Loop.Erands.DTO.DashboardDto.DashboardMetrics;

import java.util.Date;
import java.util.UUID;

public interface DashbordService {
    public DashboardMetrics getMetrics(UUID sellerId, Date from , Date to)throws Exception;
}
