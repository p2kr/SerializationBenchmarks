# Security Policy

## Supported Versions

The following versions of the Serialization Benchmarks project are currently being supported with security updates:

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |

## Reporting a Vulnerability

We take the security of the Serialization Benchmarks project seriously. If you believe you have found a security vulnerability, please report it to us as described below.

### Where to Report

Please report security vulnerabilities by opening a GitHub issue with the label "security" or by contacting the maintainers directly through the repository.

**Please do not report security vulnerabilities through public GitHub issues if they could be exploited maliciously.**

### What to Include

When reporting a vulnerability, please include:

- Type of vulnerability (e.g., deserialization issue, DoS, information disclosure)
- Full paths of source file(s) related to the vulnerability
- Location of the affected source code (tag/branch/commit or direct URL)
- Step-by-step instructions to reproduce the issue
- Proof-of-concept or exploit code (if possible)
- Impact of the vulnerability, including how an attacker might exploit it

### Response Timeline

- **Initial Response**: Within 48 hours of report submission
- **Status Update**: Within 7 days with an assessment of the report
- **Fix Timeline**: Critical vulnerabilities will be addressed within 30 days
- **Disclosure**: Coordinated disclosure after fix is available

### What to Expect

- Acknowledgment of your report within 48 hours
- Regular updates on the progress toward a fix
- Credit for the discovery (unless you prefer to remain anonymous)
- Notification when the vulnerability is fixed

## Security Considerations for Users

### Dependency Management

This project uses several third-party libraries for serialization. Users should be aware of:

1. **Jackson** - Keep updated to avoid known deserialization vulnerabilities
2. **Gson** - Monitor for security updates
3. **Protocol Buffers** - Follow Google's security advisories
4. **MessagePack** - Check for updates regularly

### Best Practices When Using This Project

#### 1. Deserialization Security

When using serialization libraries in production:

```java
// DO: Use type-safe deserialization
ObjectMapper mapper = new ObjectMapper();
mapper.activateDefaultTyping(
    mapper.getPolymorphicTypeValidator(),
    ObjectMapper.DefaultTyping.NON_FINAL
);

// DON'T: Deserialize untrusted data without validation
```

#### 2. Input Validation

Always validate data before serialization:

```java
// Validate input size limits
if (data.size() > MAX_ALLOWED_SIZE) {
    throw new IllegalArgumentException("Data exceeds maximum size");
}

// Validate data content
if (!isValidData(data)) {
    throw new IllegalArgumentException("Invalid data format");
}
```

#### 3. Resource Limits

Set appropriate limits to prevent DoS attacks:

```java
// Limit deserialization depth
mapper.setMaxDepth(10);

// Limit string length
mapper.setMaxStringLength(1_000_000);
```

#### 4. Dependency Updates

Regularly update dependencies to get security patches:

```bash
# Check for dependency updates
./mvnw versions:display-dependency-updates

# Update to latest secure versions
./mvnw versions:use-latest-releases
```

### Known Security Considerations

#### Deserialization Attacks

Serialization libraries can be vulnerable to remote code execution through malicious serialized data. This benchmark project:

- Uses controlled test data only
- Does not deserialize untrusted input
- Is intended for benchmarking, not production use

**If adapting this code for production:**
- Never deserialize data from untrusted sources without validation
- Use allow-lists for permitted classes
- Implement input validation and sanitization
- Consider using read-only deserialization where possible

#### Denial of Service (DoS)

Large or deeply nested objects can cause:
- Excessive memory consumption
- CPU exhaustion
- Stack overflow errors

**Mitigations:**
- Set maximum object depth limits
- Implement size restrictions
- Use streaming for large datasets
- Monitor resource usage

#### Information Disclosure

Serialized data may contain:
- Sensitive information in error messages
- Internal object structure details
- Type information that aids attackers

**Mitigations:**
- Sanitize error messages
- Use DTOs to control exposed data
- Don't serialize sensitive fields
- Consider encryption for sensitive data

## Dependency Security

### Current Dependencies and Known Issues

This project maintains up-to-date dependencies. Check the following resources for security advisories:

- **Jackson**: https://github.com/FasterXML/jackson/security/advisories
- **Gson**: https://github.com/google/gson/security/advisories
- **Protocol Buffers**: https://github.com/protocolbuffers/protobuf/security/advisories
- **MessagePack**: https://github.com/msgpack/msgpack-java/security

### Checking for Vulnerabilities

Use OWASP Dependency-Check:

```bash
# Add to pom.xml
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <version>9.0.0</version>
    <executions>
        <execution>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>

# Run check
./mvnw dependency-check:check
```

Or use GitHub's Dependabot by enabling it in repository settings.

## Security Update Process

When a security vulnerability is confirmed:

1. **Assessment**: Evaluate severity and impact
2. **Fix Development**: Create patch in private branch
3. **Testing**: Verify fix resolves issue without regressions
4. **Release**: Publish updated version
5. **Notification**: Alert users via:
   - GitHub Security Advisory
   - Release notes
   - README update
6. **Documentation**: Update this SECURITY.md with lessons learned

## Secure Development Practices

Contributors should follow these practices:

### Code Review

- All changes require review before merge
- Security-sensitive changes require additional scrutiny
- Use automated security scanning tools

### Testing

- Write tests for security-related fixes
- Include negative test cases
- Test with malformed/malicious input

### Dependencies

- Keep dependencies up to date
- Review dependency changes for security implications
- Use exact version pinning for reproducible builds

## License and Disclaimer

This project is licensed under the MIT License.

### MIT License

```
MIT License

Copyright (c) 2026 Serialization Benchmarks Contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

### Security Disclaimer

This software is provided for benchmarking and educational purposes. Users are responsible for:

- Conducting their own security assessments
- Ensuring compliance with their security requirements
- Testing thoroughly before production use
- Monitoring for and applying security updates

The maintainers make no warranties regarding the security of this software and are not liable for any security issues arising from its use.

## Additional Resources

### Security Best Practices

- [OWASP Deserialization Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Deserialization_Cheat_Sheet.html)
- [OWASP Dependency Check](https://owasp.org/www-project-dependency-check/)
- [Snyk Vulnerability Database](https://security.snyk.io/)
- [National Vulnerability Database](https://nvd.nist.gov/)

### Serialization Security

- [Jackson Security Guide](https://github.com/FasterXML/jackson-docs/wiki/JacksonPolymorphicDeserialization)
- [Protobuf Security](https://protobuf.dev/programming-guides/security/)
- [Java Serialization Security](https://www.oracle.com/java/technologies/javase/seccodeguide.html)

## Contact

For security concerns, please contact the project maintainers through:
- GitHub Issues (for non-critical issues)
- Direct contact (for critical vulnerabilities)

---

**Last Updated**: January 2026

This security policy is subject to change. Please check back regularly for updates.
