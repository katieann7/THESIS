import React, { useState, useEffect } from 'react';
import '../styles/AccountList.css'; // Import the CSS for Admin

const AccountList = () => {
  const [accounts, setAccounts] = useState([]);
  const [visibleDeviceIds, setVisibleDeviceIds] = useState({});
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    // Fetch accounts data from an API or external source
    const fetchAccounts = async () => {
      try {
        const response = await fetch('/api/accounts'); // Replace with your actual API endpoint
        const data = await response.json();
        setAccounts(data);
      } catch (error) {
        console.error('Error fetching accounts:', error);
      }
    };

    fetchAccounts(); // Call the function to fetch data
  }, []);

  const handleResetDeviceId = (event, accountId) => {
    event.preventDefault();
    event.stopPropagation();
    console.log(`Resetting device ID for account ${accountId}`);
  };

  const toggleDeviceIdVisibility = (event, accountId) => {
    event.preventDefault();
    event.stopPropagation();
    setVisibleDeviceIds((prevState) => ({
      ...prevState,
      [accountId]: !prevState[accountId],
    }));
  };

  const toggleAccountLock = (accountId) => {
    setAccounts((prevAccounts) =>
      prevAccounts.map((account) =>
        account.id === accountId ? { ...account, isLocked: !account.isLocked } : account
      )
    );
  };

  const handlePrintAllAccounts = () => {
    console.log('Printing all accounts');
  };

  const filteredAccounts = accounts.filter((account) =>
    account.employeeNumber.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="admin-body">
      <div className="admin-box">
        <div className="admin-header">
          <h2>Account List</h2>
        </div>

        <div className="search-container">
          <input
            type="text"
            placeholder="Search by Employee Number"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
          <button
            onClick={handlePrintAllAccounts}
            className="small-button"
          >
            Print All Accounts
          </button>
        </div>

        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Username</th>
                <th>Employee Number</th>
                <th>Device ID</th>
                <th>Is Active</th>
                <th>Is Locked</th>
              </tr>
            </thead>
            <tbody>
              {(searchTerm ? filteredAccounts : accounts).map((account) => (
                <tr key={account.id}>
                  <td>{account.accountName}</td>
                  <td>{account.employeeNumber}</td>
                  <td>
                    <span
                      onClick={(e) => toggleDeviceIdVisibility(e, account.id)}
                      className="toggle-device-id"
                    >
                      {visibleDeviceIds[account.id] ? account.deviceId : '**********'}
                    </span>
                    <span
                      onClick={(e) => handleResetDeviceId(e, account.id)}
                      className="reset-device-id"
                      title="Reset Device ID"
                    >
                      🔄
                    </span>
                  </td>
                  <td>{account.isActive ? 'Yes' : 'No'}</td>
                  <td>
                    <button onClick={() => toggleAccountLock(account.id)}>
                      {account.isLocked ? 'Unlock' : 'Lock'}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {searchTerm && filteredAccounts.length === 0 && (
          <p>No accounts found for "{searchTerm}"</p>
        )}
      </div>
    </div>
  );
};

export default AccountList;
