// src/pages/SecretPhraseModal.jsx
import React, { useState } from 'react';
import '../styles/SecretPhraseModal.css'; // Update the import path for the CSS

const SecretPhraseModal = ({ isOpen, onClose, onGenerate, secretPhrases }) => {
  const [enteredEmployeeNumber, setEnteredEmployeeNumber] = useState('');
  const [inputSecretPhrase, setInputSecretPhrase] = useState('');
  const [employeeExists, setEmployeeExists] = useState(false);

  const handleEmployeeNumberInput = (e) => {
    const input = e.target.value;
    setEnteredEmployeeNumber(input);

    // Check if employee number exists
    const employeeFound = secretPhrases.some(
      (phrase) => phrase.employeeNumber.toLowerCase() === input.toLowerCase()
    );
    setEmployeeExists(employeeFound);
  };

  const handleGenerateSecretPhrase = () => {
    if (enteredEmployeeNumber && employeeExists && inputSecretPhrase) {
      onGenerate(enteredEmployeeNumber, inputSecretPhrase);
      // Reset inputs
      setEnteredEmployeeNumber('');
      setInputSecretPhrase('');
      setEmployeeExists(false);
      onClose();
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modal">
      <div className="modal-content">
        <h3>Generate Secret Phrase</h3>
        {/* Close button moved inside the modal content */}
        <button className="close-button" onClick={onClose}>
          &times; {/* Using × for a close symbol */}
        </button>
        
        <input
          type="text"
          placeholder="Enter employee number"
          value={enteredEmployeeNumber}
          onChange={handleEmployeeNumberInput}
          className="modal-input"
        />
        {enteredEmployeeNumber && (
          employeeExists ? (
            <p style={{ color: 'green' }}>Employee found. You can enter a secret phrase.</p>
          ) : (
            <p style={{ color: 'red' }}>Employee not found. Please enter a valid employee number.</p>
          )
        )}
        <input
          type="text"
          placeholder="Enter secret phrase"
          value={inputSecretPhrase}
          onChange={(e) => setInputSecretPhrase(e.target.value)}
          className="modal-input"
        />
        <button
          onClick={handleGenerateSecretPhrase}
          disabled={!enteredEmployeeNumber || !employeeExists || !inputSecretPhrase}
          className="modal-button"
        >
          Generate
        </button>
      </div>
    </div>
  );
};

export default SecretPhraseModal;
