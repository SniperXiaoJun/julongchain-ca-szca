

package org.bcia.javachain.ca.szca.common.bcca.core.ejb.ca.revoke;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Local;

import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.certificates.certificate.CertificateDataWrapper;
import org.cesecore.certificates.certificate.CertificateRevokeException;


@Local
public interface RevocationSessionLocal extends RevocationSession {

    /**
     * Revokes a certificate, in the database and in publishers. Also handles re-activation of suspended certificates.
     *
     * Re-activating (unrevoking) a certificate have two limitations.
     * 1. A password (for for example AD) will not be restored if deleted, only the certificate and certificate status and associated info will be restored
     * 2. ExtendedInformation, if used by a publisher will not be used when re-activating a certificate 
     * 
     * The method leaves up to the caller to find the correct publishers and userDataDN.
     *
     * @param admin      Administrator performing the operation
     * @param cdw        The certificate data
     * @param publishers and array of publisher ids (Integer) of publishers to revoke the certificate in.
     * @param revocationDate from when the certificates has been revoked
     * @param reason     the reason of the revocation. (One of the RevokedCertInfo.REVOCATION_REASON constants.)
     * @param userDataDN if an DN object is not found in the certificate use object from user data instead.
     * @throws CertificaterevokeException (rollback) if certificate does not exist
     * @throws AuthorizationDeniedException (rollback)
     */
    void revokeCertificate(AuthenticationToken admin, CertificateDataWrapper cdw, Collection<Integer> publishers, Date revocationDate, int reason,
                           String userDataDN) throws CertificateRevokeException, AuthorizationDeniedException;

}
