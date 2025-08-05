package com.rmsConversion.RMSnew.Service;

import java.util.List;
import java.util.Map;

import com.rmsConversion.RMSnew.Model.ActionPassword;

public interface ActionPasswordService {
	public Map<String, Object> insertActionPassword(ActionPassword actionPassword);

	public Map<String, Object> deleteByManagerIdAndType(Long managerId, String type);

	public Map<String, Object> updatePasswordByManagerIdAndType(Long managerId, String type, String oldPassword,
			String newPassword);

	public List<String> getTypesByManagerId(Long managerId);
} 