# Variant Attribute desugaring/rehydrating issue

## Gradle issue

https://github.com/gradle/gradle/issues/29872

Reproduces `Multiple incompatible variants` error.

## Steps to reproduce

1. Publish the producer to a local repo. We want to resolve this as an external module.
```
./gradlew producer:publishMavenPublicationToLocalRepository
```

2. Reproduce failure state:
```
./gradlew consumer:test
```

3. Reproduce success with workaround applied:
```
./gradlew consumer:test -PapplyWorkaround
```

## What?

Apparently Gradle desugars strongly-typed variant attributes to `String`-typed attributes. Later,
when doing dependency resolution, it is supposed to "rehydrate" the `String`-typed attributes to the
strong types, but there was a case where this wasn't happening, leading to an issue where an
`AttributeCompatibilityRule<StrongType>` associated with a schema wasn't being called. A workaround
for this is to also add a `AttributeCompatibilityRule<String>` that reproduces the behavior of the
`StrongType` version. The code in this repo demonstrates this issue, as well as the workaround.

This issue is now fixed with https://github.com/gradle/gradle/pull/29738, but I'm filing this issue
anyway as both documentation of that issue, and more general documentation about the use-case
for using variant attributes in this particular way.

The brief summary (which I'll expand on later in blog form) is that we are migrating from a polyrepo
setup to a monorepo, which means previously unpublished modules will now be directly accessible to
each other as local project dependencies. For this reason and others, we want to define strict
dependency rules and let the Gradle dependency resolution engine enforce them. We stumbled across 
this issue because a migrated project with test fixtures was being consumed by a non-migrated 
project that wanted to use those test fixtures, leading to two variants of the producer in the 
dependency graph (both the test-fixtures variant and the main variant). The consumer didn't know
these two variants were mutually compatible because the compatibility rule wasn't being called,
because of the issue with desugaring/rehydrating. We implemented a workaround exactly as seen in
this reproducer. We can also confirm that the PR linked above and targeted for Gradle 8.10 resolves
this issue.
