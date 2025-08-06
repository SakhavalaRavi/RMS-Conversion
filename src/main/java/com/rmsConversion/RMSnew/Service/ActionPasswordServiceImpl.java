package com.rmsConversion.RMSnew.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Model.ActionPassword;
import com.rmsConversion.RMSnew.Repository.ActionPasswordRepository;

@Service
public class ActionPasswordServiceImpl implements ActionPasswordService {

	@Autowired
	private ActionPasswordRepository actionPasswordRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Map<String, Object> insertActionPassword(ActionPassword actionPassword) {
		Map<String, Object> response = new HashMap<>();

		try {
			if (actionPassword.getManagerId() == null || actionPassword.getType() == null
					|| actionPassword.getPassword() == null || actionPassword.getType().trim().isEmpty()
					|| actionPassword.getPassword().trim().isEmpty()) {

				response.put("success", false);
				response.put("message", "Manager ID, type and password are required");
				return response;
			}

			List<ActionPassword> existingList = actionPasswordRepository.findByManagerId(actionPassword.getManagerId());

			for (ActionPassword existing : existingList) {
				if (existing.getType().equalsIgnoreCase(actionPassword.getType())
						&& passwordEncoder.matches(actionPassword.getPassword(), existing.getPassword())) {
					response.put("success", false);
					response.put("message", "You have already added");
					response.put("data", null);
					return response;
				}
			}

			String encryptedPassword = passwordEncoder.encode(actionPassword.getPassword());

			int inserted = actionPasswordRepository.insertActionPassword(actionPassword.getManagerId(),
					actionPassword.getType(), encryptedPassword);

			if (inserted > 0) {
				Map<String, Object> data = new HashMap<>();
				data.put("managerId", actionPassword.getManagerId());
				data.put("type", actionPassword.getType());
				data.put("password", encryptedPassword);

				response.put("success", true);
				response.put("message", "Action password saved successfully");
				response.put("data", data);
			} else {
				response.put("success", false);
				response.put("message", "Failed to insert action password");
				response.put("data", null);
			}

		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error: " + e.getMessage());
			response.put("data", null);
		}

		return response;
	}

	public Map<String, Object> deleteByManagerIdAndType(Long managerId, String type) {
		Map<String, Object> response = new HashMap<>();

		Optional<ActionPassword> optional = actionPasswordRepository.findByManagerIdAndType(managerId, type);
		if (optional.isPresent()) {
			actionPasswordRepository.delete(optional.get());

			response.put("success", true);
			response.put("message", "Password entry deleted successfully");
		} else {
			response.put("success", false);
			response.put("message", "No record found for given managerId and type");
		}

		return response;
	}

	public Map<String, Object> updatePasswordByManagerIdAndType(Long managerId, String type, String oldPassword,
			String newPassword) {
		Map<String, Object> response = new HashMap<>();

		Optional<ActionPassword> optional = actionPasswordRepository.findByManagerIdAndType(managerId, type);
		if (optional.isPresent()) {
			ActionPassword actionPassword = optional.get();

			if (passwordEncoder.matches(oldPassword, actionPassword.getPassword())) {
				if (passwordEncoder.matches(newPassword, actionPassword.getPassword())) {
					response.put("success", false);
					response.put("message", "New password cannot be same as old password");
				} else {
					actionPassword.setPassword(passwordEncoder.encode(newPassword));
					actionPasswordRepository.save(actionPassword);

					response.put("success", true);
					response.put("message", "Password updated successfully");
				}

			} else {
				response.put("success", false);
				response.put("message", "Old password is incorrect");
			}
		} else {
			response.put("success", false);
			response.put("message", "No record found for managerId and type");
		}

		return response;
	}

	public List<String> getTypesByManagerId(Long managerId) {
		return actionPasswordRepository.findDistinctTypesByManagerId(managerId);
	}
} 