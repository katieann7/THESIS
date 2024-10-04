import React, { useState, useEffect } from 'react';
import '../styles/SecretPhrases.css'; // Import the CSS for Secret Phrases
import SecretPhraseModal from './SecretPhraseModal'; // Import the modal component

const SecretPhrases = () => {
  const [secretPhrases, setSecretPhrases] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedPhrases, setSelectedPhrases] = useState(new Set());
  const [visibleSecretPhrases, setVisibleSecretPhrases] = useState({});
  const [searchQuery, setSearchQuery] = useState('');
  const [showSelectedOnly, setShowSelectedOnly] = useState(false);

  useEffect(() => {
    // Fetch secret phrases from an external source
    const fetchSecretPhrases = async () => {
      try {
        const response = await fetch('/api/secret-phrases'); // Replace with actual API endpoint
        const data = await response.json();
        setSecretPhrases(data);
      } catch (error) {
        console.error('Error fetching secret phrases:', error);
      }
    };

    fetchSecretPhrases(); // Call the fetch function
  }, []);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleGenerateSecretPhrase = (employeeNumber, newPhrase) => {
    const updatedPhrases = secretPhrases.map((phrase) =>
      phrase.employeeNumber.toLowerCase() === employeeNumber.toLowerCase()
        ? { ...phrase, phrase: newPhrase }
        : phrase
    );
    setSecretPhrases(updatedPhrases);
  };

  const handlePrintSelectedPhrases = () => {
    const selectedIds = Array.from(selectedPhrases);
    console.log(`Printing selected secret phrases for IDs: ${selectedIds.join(', ')}`);
  };

  const toggleSecretPhraseVisibility = (phraseId) => {
    setVisibleSecretPhrases((prevState) => ({
      ...prevState,
      [phraseId]: !prevState[phraseId],
    }));
  };

  const handleCheckboxChange = (phraseId) => {
    setSelectedPhrases((prevSelected) => {
      const newSelected = new Set(prevSelected);
      if (newSelected.has(phraseId)) {
        newSelected.delete(phraseId);
      } else {
        newSelected.add(phraseId);
      }
      return newSelected;
    });
  };

  const filteredSecretPhrases = secretPhrases.filter((phrase) => {
    const matchesSearchQuery = phrase.employeeNumber.toLowerCase().includes(searchQuery.toLowerCase());
    const isSelected = selectedPhrases.has(phrase.id);
    return matchesSearchQuery && (!showSelectedOnly || isSelected);
  });

  return (
    <div className="admin-body">
      <div className="admin-box">
        <div className="admin-header">
          <h2>Secret Phrase Generator</h2>
        </div>

        <div className="search-container">
          <input
            type="text"
            placeholder="Search Employee Number"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="search-input"
          />
          <button onClick={openModal} className="small-button">Add Secret Phrase</button>
          <button
            onClick={handlePrintSelectedPhrases}
            className="small-button"
            disabled={selectedPhrases.size === 0}
          >
            Print Secret Phrase
          </button>
          <button
            onClick={() => setShowSelectedOnly((prev) => !prev)}
            className={`small-button ${showSelectedOnly ? 'active' : ''}`}
          >
            {showSelectedOnly ? 'Show All' : 'Show Selected'}
          </button>
        </div>

        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Select</th>
                <th>Username</th>
                <th>Employee Number</th>
                <th>Secret Phrase</th>
              </tr>
            </thead>
            <tbody>
              {filteredSecretPhrases.map((phrase) => (
                <tr key={phrase.id} className={selectedPhrases.has(phrase.id) ? 'selected-row' : ''}>
                  <td>
                    <input
                      type="checkbox"
                      checked={selectedPhrases.has(phrase.id)}
                      onChange={() => handleCheckboxChange(phrase.id)}
                    />
                  </td>
                  <td>{phrase.user}</td>
                  <td>{phrase.employeeNumber}</td>
                  <td>
                    <span
                      onClick={() => toggleSecretPhraseVisibility(phrase.id)}
                      className="toggle-phrase"
                    >
                      {visibleSecretPhrases[phrase.id] ? phrase.phrase : '**********'}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <SecretPhraseModal
          isOpen={isModalOpen}
          onClose={closeModal}
          onGenerate={handleGenerateSecretPhrase}
          secretPhrases={secretPhrases}
        />
      </div>
    </div>
  );
};

export default SecretPhrases;
