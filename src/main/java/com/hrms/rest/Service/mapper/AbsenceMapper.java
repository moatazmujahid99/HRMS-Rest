package com.hrms.rest.Service.mapper;

import java.util.ArrayList;
import java.util.List;

import com.hrms.rest.persistence.entity.Absence;
import com.hrms.rest.presentation.dto.AbsenceDTO;
import com.hrms.rest.presentation.enums.LeaveStatus;
import com.hrms.rest.presentation.enums.LeaveType;

public class AbsenceMapper {

    public static AbsenceDTO AbsenceToAbsenceDTO(Absence absence) {
        return AbsenceDTO.builder()
                .leaveId(absence.getLeaveId())
                .leaveStartDate(absence.getLeaveStartDate())
                .leaveEndDate(absence.getLeaveEndDate())
                .leaveType(LeaveType.valueOf(absence.getLeaveType()))
                .leaveStatus(LeaveStatus.valueOf(absence.getLeaveStatus()))
                .build();
    }

    public static List<AbsenceDTO> AbsencesToAbsenceDTOs(List<Absence> absences) {
        List<AbsenceDTO> absenceDTOs = new ArrayList<>();
        for (Absence absence : absences) {
            AbsenceDTO absenceDTO = AbsenceToAbsenceDTO(absence);
            absenceDTOs.add(absenceDTO);
        }
        return absenceDTOs;
    }

}
